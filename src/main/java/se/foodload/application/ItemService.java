package se.foodload.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import se.foodload.application.exception.InsertionException;
import se.foodload.application.interfaces.IItemService;
import se.foodload.application.exception.ConflictException;

import se.foodload.application.exception.NotFoundException;

import se.foodload.application.exception.dto.ConflictDTO;
import se.foodload.domain.Family;
import se.foodload.domain.Item;
import se.foodload.domain.ItemCount;
import se.foodload.domain.StorageType;
import se.foodload.dtos.ItemCountDTO;
import se.foodload.enums.ErrorEnums;
import se.foodload.redis.RedisMessagePublisher;
import se.foodload.repository.ItemCountRepository;
import se.foodload.repository.ItemRepository;
import se.foodload.repository.StorageTypeRepository;
import se.foodload.repository.utils.OffsetLimitPageable;

@Service
@Transactional
public class ItemService implements IItemService {

	@Autowired
	ItemRepository itemRepo;
	@Autowired
	ItemCountRepository itemCountRepo;
	@Autowired
	StorageTypeRepository storageTypeRepo;
	@Autowired
	RedisMessagePublisher redisMessagePublisher;

	@Override
	public Item findItem(String qrCode) {

		Optional<Item> item = itemRepo.findByQrCode(qrCode);
		if (item.isEmpty()) {
			throw new NotFoundException(ErrorEnums.ITEM_QR_NOT_FOUND.toString());
		}
		return item.get();
	}

	@Override
	public List<Item> findItemPattern(String pattern, int start) {
		String trimmedPattern = pattern.trim();
		if (trimmedPattern.length() == 0) {
			return new ArrayList<>();
		}

		Optional<List<Item>> result = itemRepo.findMatchingItems(trimmedPattern.toLowerCase(),
				new OffsetLimitPageable(start, 30));

		if (result.isEmpty()) {
			return new ArrayList<>();
		} else {
			return result.get();
		}
	}

	@Override
	public void incrementItem(String clientId, long itemCountId, long familyId) {
		Optional<ItemCount> itemCount = itemCountRepo.findByItemCountIdAndFamilyId(itemCountId, familyId);
		if (itemCount.isEmpty()) {
			throw new NotFoundException(ErrorEnums.ITEM_COUNT_NOT_FOUND.toString());
		}
		ItemCount ic = itemCount.get();
		ic.incrementItemCount();
		itemCountRepo.save(ic);
		Item item = ic.getItem();
		int amount = ic.getCount();
		String storageType = ic.getStorageType().getName();
		redisMessagePublisher.publishItem(itemCountId, item, clientId, familyId, amount, storageType);
	}

	@Override
	public void decrementItem(String clientId, long itemCountId, long familyId) {
		Optional<ItemCount> itemCount = itemCountRepo.findByItemCountIdAndFamilyId(itemCountId, familyId);
		if (itemCount.isEmpty()) {
			throw new NotFoundException(ErrorEnums.ITEM_COUNT_NOT_FOUND.toString());
		}
		ItemCount ic = itemCount.get();
		ic.decrementItemCount();
		itemCountRepo.save(ic);
		Item item = ic.getItem();
		int amount = ic.getCount();
		String storageType = ic.getStorageType().getName();
		redisMessagePublisher.publishItem(itemCountId, item, clientId, familyId, amount, storageType);
	}

	@Override
	public void addItem(String clientId, Family family, String qrCode, String storageType, int amount) {
		Item item = findItem(qrCode);
		Optional<ItemCount> itemCount = itemCountRepo.findByQrcodeAndFamilyIdAndStorageType(qrCode, family.getId(),
				storageType);
		if (itemCount.isEmpty()) {
			int iCount = itemCountRepo.insertItemCount(qrCode, family.getId(), storageType, amount);
			if (iCount == 0) {
				throw new InsertionException(ErrorEnums.INSERTION_FAIL.toString());
			}
			Optional<ItemCount> ic = itemCountRepo.findByQrcodeAndFamilyIdAndStorageType(qrCode, family.getId(),
					storageType);
			if (ic.isEmpty()) {
				throw new NotFoundException(ErrorEnums.ITEM_COUNT_NOT_FOUND.toString());
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
	@Deprecated
	public void removeItem(String clientId, Family family, String qrCode, String storageType, int amount) {
		Item item = findItem(qrCode);

		Optional<ItemCount> itemCount = itemCountRepo.findByQrcodeAndFamilyIdAndStorageType(qrCode, family.getId(),
				storageType);
		if (itemCount.isEmpty()) {
			throw new NotFoundException(ErrorEnums.ITEM_COUNT_NOT_FOUND.toString());
		}
		ItemCount ic = itemCount.get();
		ic.removeItemCount(amount);
		itemCountRepo.save(ic);
		long itemCountId = ic.getId();
		int newAmount = ic.getCount();
		redisMessagePublisher.publishItem(itemCountId, item, clientId, family.getId(), newAmount, storageType);
	}

	@Override
	public List<ItemCount> getAllItemCounts(Family family) {
		Optional<List<ItemCount>> itemCounts = itemCountRepo.findByFamilyId(family);
		if (itemCounts.isEmpty()) {
			return new ArrayList<>();
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

	@Override
	public int moveItemTo(long familyId, long itemCountId, String clientId, String destStorageType, int moveAmount,
			int oldAmount) {
		ItemCount destItemCount;

		Optional<ItemCount> optItemCount = itemCountRepo.findByItemCountIdAndFamilyId(itemCountId, familyId);
		if (optItemCount.isEmpty()) {
			throw new NotFoundException(ErrorEnums.ITEM_COUNT_NOT_FOUND.toString());
		}
		ItemCount srcItemCount = optItemCount.get();
		String qrCode = srcItemCount.getItem().getQrCode();

		int icCount = srcItemCount.getCount();
		if (icCount != oldAmount) {
			throw new ConflictException(ErrorEnums.ITEM_MOVE_CONFLICT.toString(), new ConflictDTO(icCount));
		}

		if (moveAmount > icCount) {
			throw new IllegalArgumentException("Not enough items to move");
		}

		srcItemCount.setCount(icCount - moveAmount);
		itemCountRepo.save(srcItemCount);

		optItemCount = itemCountRepo.findByQrcodeAndFamilyIdAndStorageType(qrCode, familyId, destStorageType);
		if (optItemCount.isEmpty()) {
			// Create new
			int itemCountRow = itemCountRepo.insertItemCount(qrCode, familyId, destStorageType, moveAmount);
			if (itemCountRow == 0) {
				throw new InsertionException(ErrorEnums.INSERTION_FAIL.toString());
			}
			optItemCount = itemCountRepo.findByQrcodeAndFamilyIdAndStorageType(qrCode, familyId, destStorageType);
			if (optItemCount.isEmpty()) {
				throw new NotFoundException(ErrorEnums.ITEM_COUNT_NOT_FOUND.toString());
			} else {
				destItemCount = optItemCount.get();
			}
		} else {
			// Update existing
			destItemCount = optItemCount.get();
			destItemCount.addItemCount(moveAmount);
			itemCountRepo.save(destItemCount);
		}

		ItemCountDTO srcItemCountDTO = new ItemCountDTO(srcItemCount);
		ItemCountDTO destItemCountDTO = new ItemCountDTO(destItemCount);
		redisMessagePublisher.publishMoveItem(clientId, familyId, srcItemCountDTO, destItemCountDTO);
		return srcItemCountDTO.getAmount();
	}

	@Override
	public int moveItemFrom(long familyId, long itemCountId, String clientId, String srcStorageType, int moveAmount,
			int oldAmount) {
		Optional<ItemCount> optItemCount = itemCountRepo.findByItemCountIdAndFamilyId(itemCountId, familyId);
		if (optItemCount.isEmpty()) {
			throw new NotFoundException(ErrorEnums.ITEM_COUNT_NOT_FOUND.toString());
		}
		ItemCount destItemCount = optItemCount.get(); // the destination is the sent in itemCount
		String qrCode = destItemCount.getItem().getQrCode();

		int destICCount = destItemCount.getCount();
		if (destICCount != oldAmount) {
			throw new ConflictException(ErrorEnums.ITEM_MOVE_CONFLICT.toString(), new ConflictDTO(destICCount));
		}

		optItemCount = itemCountRepo.findByQrcodeAndFamilyIdAndStorageType(qrCode, familyId, srcStorageType);

		if (optItemCount.isEmpty()) {
			throw new NotFoundException("Could not find item in other storage");
		}

		// Move from src storage
		ItemCount srcItemCount = optItemCount.get();

		if (moveAmount > srcItemCount.getCount()) {
			throw new IllegalArgumentException("The storage does not have enough items to move");
		}

		srcItemCount.setCount(srcItemCount.getCount() - moveAmount);
		destItemCount.setCount(destICCount + moveAmount);
		itemCountRepo.save(srcItemCount);
		itemCountRepo.save(destItemCount);

		ItemCountDTO srcItemCountDTO = new ItemCountDTO(srcItemCount);
		ItemCountDTO destItemCountDTO = new ItemCountDTO(destItemCount);
		redisMessagePublisher.publishMoveItem(clientId, familyId, srcItemCountDTO, destItemCountDTO);
		return destItemCountDTO.getAmount();
	}

	@Override
	public int changeItemCount(long itemCountId, long familyId, String clientId, int oldCount, int newCount) {
		Optional<ItemCount> optItemCount = itemCountRepo.findByItemCountIdAndFamilyId(itemCountId, familyId);
		if (optItemCount.isEmpty()) {
			throw new NotFoundException(ErrorEnums.ITEM_COUNT_NOT_FOUND.toString());
		}
		ItemCount ic = optItemCount.get();
		int currCount = ic.getCount();
		if (currCount != oldCount) {
			throw new ConflictException(ErrorEnums.ITEM_MOVE_CONFLICT.toString(), new ConflictDTO(currCount));
		}
		ic.setCount(newCount);
		itemCountRepo.save(ic);
		redisMessagePublisher.publishItem(itemCountId, ic.getItem(), clientId, familyId, newCount,
				ic.getStorageType().getName());
		return newCount;
	}

	@Override
	public void deleteItem(long familyId, long itemCountId, String clientId, int amount) {
		Optional<ItemCount> optItemCount = itemCountRepo.findByItemCountIdAndFamilyId(itemCountId, familyId);
		if (optItemCount.isEmpty()) {
			throw new NotFoundException(ErrorEnums.ITEM_COUNT_NOT_FOUND.toString());
		}
		ItemCount ic = optItemCount.get();
		if (amount != ic.getCount()) {
			throw new ConflictException(ErrorEnums.ITEM_MOVE_CONFLICT.toString(), new ConflictDTO(ic.getCount()));
		}
		itemCountRepo.delete(ic);
		redisMessagePublisher.publishDeleteItem(clientId, familyId, itemCountId);
	}

	@Override
	public void addItemToDb(String qr, String name, String brand){
		Item newItem = new Item(name, brand, qr);
		itemRepo.save(newItem);
	}

	private StorageType findStorageType(String storageName) {
		Optional<StorageType> storageType = storageTypeRepo.findByName(storageName);
		if (storageType.isEmpty()) {
			throw new NotFoundException(ErrorEnums.STORAGE_TYPE_NOT_FOUND.toString());
		}
		return storageType.get();
	}

	private ItemCount findItemCount(StorageType storageType, Item item) {
		Optional<ItemCount> itemCount = itemCountRepo.findByStorageTypeAndItem(storageType, item);
		if (itemCount.isEmpty()) {
			throw new NotFoundException(ErrorEnums.ITEM_COUNT_NOT_FOUND.toString());
		}
		return itemCount.get();
	}
}
