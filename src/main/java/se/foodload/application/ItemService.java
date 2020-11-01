package se.foodload.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.foodload.application.interfaces.IItemService;
import se.foodload.application.exception.ItemCountNotFoundException;
import se.foodload.application.exception.ItemNotFoundException;
import se.foodload.application.exception.StorageTypeNotFoundException;
import se.foodload.domain.Family;
import se.foodload.domain.Item;
import se.foodload.domain.ItemCount;
import se.foodload.domain.StorageType;
import se.foodload.enums.ErrorEnums;
import se.foodload.redis.RedisMessagePublisher;
import se.foodload.repository.ItemCountRepository;
import se.foodload.repository.ItemRepository;
import se.foodload.repository.StorageRepository;
import se.foodload.repository.StorageTypeRepository;
import se.foodload.repository.utils.OffsetLimitPageable;

@Service
public class ItemService implements IItemService {
    private final String STORAGE_TYPE_NOT_FOUND = ErrorEnums.STORAGETYPENOTFOUND.getErrorMsg();
    // private final String STORAGE_NOT_FOUND_FAMILY =
    // ErrorEnums.STORAGENOTFOUNDFAMILY.getErrorMsg();
    // private final String STORAGE_NOT_FOUND_FAMILY_2 =
    // ErrorEnums.STORAGENOTFOUNDFAMILY2.getErrorMsg();
    private final String ITEM_NOT_FOUND = ErrorEnums.ITEMNOTFOUND.getErrorMsg();
    private final String ITEM_NOT_FOUND_CONTAININ = ErrorEnums.ITEMNOTFOUNDCONTAININGNAME.getErrorMsg();
    private final String ITEM_COUNT_NOT_FOUND_ID = ErrorEnums.ITEMCOUNTNOTFOUNDID.getErrorMsg();
    private final String ITEM_COUNT_NOT_FOUND_ID_2 = ErrorEnums.ITEMCOUNTNOTFOUNDID2.getErrorMsg();
    private final String ITEM_COUNT_QFS = ErrorEnums.ITEMCOUNTQFS.getErrorMsg();
    private final String ITEM_COUNT_QFS_2 = ErrorEnums.ITEMCOUNTQSF2.getErrorMsg();
    private final String ITEM_COUNT_QFS_3 = ErrorEnums.ITEMCOUNTQSF3.getErrorMsg();

    @Autowired
    ItemRepository itemRepo;
    @Autowired
    StorageService storageService;
    @Autowired
    ItemCountRepository itemCountRepo;
    @Autowired
    StorageRepository storageRepo;
    @Autowired
    StorageTypeRepository storageTypeRepo;
    @Autowired
    RedisMessagePublisher redisMessagePublisher;

    @Override
    public Item findItem(String qrCode) {

        Optional<Item> item = itemRepo.findByQrCode(qrCode);
        if (item.isEmpty()) {
            throw new ItemNotFoundException(ITEM_NOT_FOUND + qrCode);
        }
        return item.get();
    }

    @Override
    public List<Item> findItemPattern(String pattern, int start) {
        String trimmedPattern = pattern.trim();
        Optional<List<Item>> result = null;
        if (trimmedPattern.length() == 0) {
            return new ArrayList<Item>();
        }

        result = itemRepo.findMatchingItems(trimmedPattern.toLowerCase(), new OffsetLimitPageable(start, 30));

        if (result.isEmpty()) {
            return new ArrayList<Item>();
        } else {
            return result.get();
        }
    }

    @Override
    public void incrementItem(String clientId, long itemcountId, long familyId) {
        Optional<ItemCount> itemCount = itemCountRepo.findByItemCountIdAndFamilyId(itemcountId, familyId);
        if (itemCount.isEmpty()) {
            throw new ItemCountNotFoundException(
                    ITEM_COUNT_NOT_FOUND_ID + itemcountId + ITEM_COUNT_NOT_FOUND_ID_2 + familyId);
        }
        ItemCount ic = itemCount.get();
        ic.incrementItemCount();
        itemCountRepo.save(ic);
        Item item = ic.getItem();
        int amount = ic.getCount();
        String storageType = ic.getStorageType().getName();
        redisMessagePublisher.publishItem(itemcountId, item, clientId, familyId, amount, storageType);

    }

    @Override
    public void decrementItem(String clientId, long itemcountId, long familyId) {
        Optional<ItemCount> itemCount = itemCountRepo.findByItemCountIdAndFamilyId(itemcountId, familyId);
        if (itemCount.isEmpty()) {
            throw new ItemCountNotFoundException(
                    ITEM_COUNT_NOT_FOUND_ID + itemcountId + ITEM_COUNT_NOT_FOUND_ID_2 + familyId);
        }
        ItemCount ic = itemCount.get();
        ic.decrementItemCount();
        itemCountRepo.save(ic);
        Item item = ic.getItem();
        int amount = ic.getCount();
        String storageType = ic.getStorageType().getName();
        redisMessagePublisher.publishItem(itemcountId, item, clientId, familyId, amount, storageType);
    }

    /**
     * Alter how the item is created, dont use custom sql, instead inserat new
     * itemcount ... for optimisation.
     */
    @Override
    public void addItem(String clientId, Family family, String qrCode, String storageType, int amount) {
        Item item = findItem(qrCode);
        Optional<ItemCount> itemCount = itemCountRepo.findByQrcodeAndFamilyIdAndStorageType(qrCode, family.getId(),
                storageType);
        if (itemCount.isEmpty()) {
            int iCount = itemCountRepo.insertItemCount(qrCode, family.getId(), storageType, amount);
            if (iCount == 0) {
                // Throw insert failed..
            }
            Optional<ItemCount> ic = itemCountRepo.findByQrcodeAndFamilyIdAndStorageType(qrCode, family.getId(),
                    storageType);
            if (ic.isEmpty()) {
                throw new ItemCountNotFoundException(
                        ITEM_COUNT_QFS + qrCode + ITEM_COUNT_QFS_2 + storageType + ITEM_COUNT_QFS_3 + family.getId());
            }
            long itemCountId = ic.get().getId();
            redisMessagePublisher.publishItem(itemCountId, item, clientId, family.getId(), amount, storageType);
        } else {
            itemCount.get().addItemCount(amount);
            itemCountRepo.save(itemCount.get());
            int newAmount = itemCount.get().getCount();
            long itemCountId = itemCount.get().getId();
            redisMessagePublisher.publishItem(itemCountId, item, clientId, family.getId(), newAmount, storageType);
        }

    }

    @Override
    public void deleteItem(String clientId, Family family, String qrCode, String storageType, int amount) {
        Item item = findItem(qrCode);

        Optional<ItemCount> itemCount = itemCountRepo.findByQrcodeAndFamilyIdAndStorageType(qrCode, family.getId(),
                storageType);
        if (itemCount.isEmpty()) {
            throw new ItemCountNotFoundException(
                    ITEM_COUNT_QFS + qrCode + ITEM_COUNT_QFS_2 + storageType + ITEM_COUNT_QFS_3 + family.getId());
        }
        ItemCount ic = itemCount.get();
        ic.removeItemCount(amount);
        itemCountRepo.save(ic);
        long itemcountId = ic.getId();
        int newAmount = ic.getCount();
        redisMessagePublisher.publishItem(itemcountId, item, clientId, family.getId(), newAmount, storageType);
    }

    @Override
    public List<ItemCount> getAllItemCounts(Family family){
        Optional<List<ItemCount>> itemCounts = itemCountRepo.findByfamilyId(family);
        if (itemCounts.isEmpty()) {
            return new ArrayList<ItemCount>();
        }
        return itemCounts.get();
    }

    @Override
    public void alterStorage(Family family, String qrCode, String storageName, String newStorageName) {
        Item item = findItem(qrCode);
        StorageType storageType = findStorageType(storageName);
        StorageType newStorageType = findStorageType(newStorageName);

        ItemCount itemCount = findItemCount(storageType, item);
        itemCount.setStorageType(newStorageType);
        itemCountRepo.save(itemCount);

    }

    private StorageType findStorageType(String storageName) {
        Optional<StorageType> storageType = storageTypeRepo.findByName(storageName);
        if (storageType.isEmpty()) {
            throw new StorageTypeNotFoundException(STORAGE_TYPE_NOT_FOUND + storageType);
        }
        return storageType.get();
    }

    /*
     * private Storage findStorage(Family family, StorageType storageType) {
     * Optional<Storage> storage = storageRepo.findByFamilyIdAndStorageType(family,
     * storageType); if (storage.isEmpty()) { throw new StorageNotFoundException(
     * STORAGE_NOT_FOUND_FAMILY + family.getId() + STORAGE_NOT_FOUND_FAMILY_2 +
     * storageType); } return storage.get(); }
     */
    private ItemCount findItemCount(StorageType storageType, Item item) {
        Optional<ItemCount> itemCount = itemCountRepo.findByStorageTypeAndItem(storageType, item);
        if (itemCount.isEmpty()) {
            throw new ItemCountNotFoundException(
                    ITEM_COUNT_QFS + item.getQrCode() + ITEM_COUNT_QFS_2 + storageType.getName());
        }
        return itemCount.get();
    }

}
