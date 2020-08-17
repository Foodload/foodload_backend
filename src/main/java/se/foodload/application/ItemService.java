package se.foodload.application;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.foodload.application.Interfaces.IItemService;
import se.foodload.application.exception.ItemCountNotFoundException;
import se.foodload.application.exception.ItemNotFoundException;
import se.foodload.application.exception.StorageNotFoundException;
import se.foodload.application.exception.StorageTypeNotFoundException;
import se.foodload.domain.Family;
import se.foodload.domain.Item;
import se.foodload.domain.ItemCount;
import se.foodload.domain.Storage;
import se.foodload.domain.StorageType;
import se.foodload.enums.StorageTypeEnums;
import se.foodload.redis.RedisMessagePublisher;
import se.foodload.repository.ItemCountRepository;
import se.foodload.repository.ItemRepository;
import se.foodload.repository.StorageRepository;
import se.foodload.repository.StorageTypeRepository;

@Service
public class ItemService implements IItemService {
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
			throw new ItemNotFoundException("Item with qrCode " +qrCode +" could not be found");
		}
		return item.get();

	}
	
	public void incrementItem(String clientId, long itemcountId, long familyId) {
		Optional<ItemCount> itemCount= itemCountRepo.findByItemCountIdAndFamilyId(itemcountId, familyId);
		if (itemCount.isEmpty()) {
			throw new ItemCountNotFoundException("ItemCount with id: "+itemcountId+" does not belong to familyId: "+familyId);
		}	
			ItemCount ic = itemCount.get();
			ic.incrementItemCount();
			itemCountRepo.save(ic);
			Item item = ic.getItem();
			int amount = ic.getCount();
			
			redisMessagePublisher.publishItem(itemcountId , item, clientId, familyId, amount );
	
	}
	
	@Override
	public void addItem(String clientId, Family family, String qrCode, String storageName, int amount) {
		Item item = findItem(qrCode);
		Optional<ItemCount> itemCount = itemCountRepo.findByQrcodeAndFamilyIdAndStorageType(qrCode, family.getId(), storageName);
		if (itemCount.isEmpty()) {
			int iCount = itemCountRepo.insertItemCount(qrCode, family.getId(), storageName, amount);
			if(iCount == 0) {
				//Throw insert failed..
			}
			Optional<ItemCount> ic = itemCountRepo.findByQrcodeAndFamilyIdAndStorageType(qrCode, family.getId(), storageName);			
			if(ic.isEmpty()) {
				throw new ItemCountNotFoundException("Item with qrCode "+qrCode+" does not exist in "+ storageName +" for family: "+family.getId());
			}
			long itemcountId = ic.get().getId();
			redisMessagePublisher.publishItem(itemcountId,item, clientId, family.getId(), 1 );
		}
		else {
			itemCount.get().addItemCount(amount);
			itemCountRepo.save(itemCount.get());
			int newAmount = itemCount.get().getCount();
			long itemcoundId = itemCount.get().getId();
			redisMessagePublisher.publishItem(itemcoundId,item, clientId, family.getId(), newAmount );
		}
	
		
	}

	@Override
	public void deleteItem(String clientId, Family family, String qrCode, String storageName, int amount) {
		Item item = findItem(qrCode);
		
		Optional<ItemCount> itemCount = itemCountRepo.findByQrcodeAndFamilyIdAndStorageType(qrCode, family.getId(), storageName);
		if (itemCount.isEmpty()) {
			throw new ItemCountNotFoundException("Item with qrCode "+qrCode+" does not exist in "+ storageName +" for family: "+family.getId());
		}
		ItemCount ic = itemCount.get();
		ic.removeItemCount(amount);
		itemCountRepo.save(ic);
		long itemcountId = ic.getId();
		int newAmount = ic.getCount();
		redisMessagePublisher.publishItem( itemcountId, item, clientId, family.getId(), newAmount);
	}

	public void alterStroage(Family family, String qrCode, String storageName, String newStorageName) {
		Item item = findItem(qrCode);
		StorageType storageType = findStorageType(storageName);
		StorageType newStorageType = findStorageType(newStorageName);
		Storage storage = findStorage(family, storageType);
		Storage newStorage = findStorage(family, newStorageType);
		
		ItemCount itemCount= findItemCount(storage, item);
		itemCount.setStorage(newStorage);
		itemCountRepo.save(itemCount);
		
		
	
	}
	private StorageType findStorageType(String storageName) {
		Optional<StorageType> storageType = storageTypeRepo.findByName(storageName);
		if(storageType.isEmpty()) {
			throw new StorageTypeNotFoundException("No storagetype with name " + storageType+ " could be found.");
		}
		return storageType.get();
	}
	private Storage findStorage(Family family, StorageType storageType) {
		Optional<Storage> storage = storageRepo.findByFamilyIdAndStorageType(family, storageType);
		if(storage.isEmpty()) {
			throw new StorageNotFoundException("Storage for family "+ family.getId()+ " with storageType "+storageType +" could not be found");
		}
		return storage.get();
	}
	private ItemCount findItemCount(Storage storage, Item item) {
		Optional<ItemCount> itemCount = itemCountRepo.findByStorageAndItem(storage, item);
		if (itemCount.isEmpty()) {
			throw new ItemCountNotFoundException("Item with qrCode "+item.getQrCode()+" does not exist in "+ storage.getStorageType().getName());
		}
		return itemCount.get();
	}
}
/*
@Override
public void addItem(Family family, String qrCode, String storageName, int ammount) {
	

	
	List<Storage> storages = storageService.getStorages(family);
	Optional<Item> item = itemRepo.findByQrCode(qrCode);
	if (item.isEmpty()) {
		throw new ItemNotFoundException("Item with qrCode " +qrCode +" could not be found");
	}
	Item foundItem = item.get();
	
	for (Storage storage : storages) {
		if (storage.getStorageType().getName().contentEquals(storageType)) {
			Optional<ItemCount> itemCount = itemCountRepo.findBystorageIdAndItemId(storage, foundItem);
			if (itemCount.isEmpty()) {
				ItemCount newItemCount = new ItemCount(storage, foundItem);
				itemCountRepo.save(newItemCount);
			}
			else {
				itemCount.get().addItemCount(ammount);
				itemCountRepo.save(itemCount.get());
			}
		} else {
			throw new StorageTypeNotFoundException("No storagetype with name " + storageType+ " could be found.");
		}
	}
	@Override
	public void deleteItem(Family family, String qrCode, String storageName, int ammount) {
		
		List<Storage> storages = storageService.getStorages(family);
		Optional<Item> item = itemRepo.findByQrCode(qrCode);
		if (item.isEmpty()) {
			throw new ItemNotFoundException("Item with qrCode " +qrCode +" could not be found");
		}
		Item foundItem = item.get();
		for (Storage storage : storages) {
			if (storage.getStorageType().getName().contentEquals(storageType)) {
				Optional<ItemCount> itemCount = itemCountRepo.findBystorageIdAndItemId(storage, foundItem);
				if (itemCount.isEmpty()) {
					throw new ItemCountNotFoundException("Item with qrCode "+qrCode+" does not exist in "+ storageType);

				}
				itemCount.get().removeItemCount();
				itemCountRepo.save(itemCount.get());
			} else {
				throw new StorageTypeNotFoundException("No storagetype with name " + storageType+" could be found.");
			}
		}

	}
	
	
	public void alterStroage(Family family, String qrCode, String storageName, String newStorageName) {
		List<Storage> storages = storageService.getStorages(family);
		Optional<Item> item = itemRepo.findByQrCode(qrCode);
		if (item.isEmpty()) {
			// throw error item not found.
		}
		Item foundItem = item.get();
		Storage newStorage = null;
		for (Storage storage : storages) {
			if (storage.getStorageType().getName().contentEquals(newStorageType)) {
				newStorage = storage;
			}
		}
		for (Storage storage : storages) {
			if (storage.getStorageType().getName().contentEquals(storageType)) {
				Optional<ItemCount> itemCount = itemCountRepo.findBystorageIdAndItemId(storage, foundItem);
				if (itemCount.isEmpty()) {
					throw new ItemCountNotFoundException("Item with qrCode "+qrCode+" does not exist in "+ storageType);
				}
				itemCount.get().setStorageId(newStorage);
				itemCountRepo.save(itemCount.get());
			} else {
				throw new StorageTypeNotFoundException("No storagetype with name " + storageType+" could be found.");
			}

		}
	}
	*/