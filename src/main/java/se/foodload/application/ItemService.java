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

	@Override
	public Item findItem(String qrCode) {
	
		Optional<Item> item = itemRepo.findByQrCode(qrCode);
		if (item.isEmpty()) {
			throw new ItemNotFoundException("Item with qrCode " +qrCode +" could not be found");
		}
		return item.get();

	}

	@Override
	public void addItem(Family family, String qrCode, String storageName, int ammount) {
		
		Item foundItem = findItem(qrCode);
		StorageType storageType = findStorageType(storageName);
		Storage storage = findStorage(family, storageType);
		Optional<ItemCount> itemCount = itemCountRepo.findBystorageIdAndItemId(storage, foundItem);
		if (itemCount.isEmpty()) {
			ItemCount newItemCount = new ItemCount(storage, foundItem);
			itemCountRepo.save(newItemCount);
		}
		else {
			itemCount.get().addItemCount(ammount);
			itemCountRepo.save(itemCount.get());
			System.out.println(itemCount.get());
		}
		
		/*
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
		}*/
	}

	@Override
	public void deleteItem(Family family, String qrCode, String storageName, int ammount) {
		Item item = findItem(qrCode);
		StorageType storageType = findStorageType(storageName);
		Storage storage = findStorage(family, storageType);
		Optional<ItemCount> itemCount = itemCountRepo.findBystorageIdAndItemId(storage, item);
		if (itemCount.isEmpty()) {
			throw new ItemCountNotFoundException("Item with qrCode "+qrCode+" does not exist in "+ storageType);
		}
		itemCount.get().removeItemCount(ammount);
		itemCountRepo.save(itemCount.get());
		
		/*List<Storage> storages = storageService.getStorages(family);
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
		}*/

	}

	public void alterStroage(Family family, String qrCode, String storageName, String newStorageName) {
		Item item = findItem(qrCode);
		StorageType storageType = findStorageType(storageName);
		StorageType newStorageType = findStorageType(newStorageName);
		Storage storage = findStorage(family, storageType);
		Storage newStorage = findStorage(family, newStorageType);
		
		ItemCount itemCount= findItemCount(storage, item);
		itemCount.setStorageId(newStorage);
		itemCountRepo.save(itemCount);
		
		
		
		/*List<Storage> storages = storageService.getStorages(family);
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

		}*/
	}


	private StorageType findStorageType(String storageName) {
		Optional<StorageType> storageType = storageTypeRepo.findByName(storageName);
		if(storageType.isEmpty()) {
			throw new StorageTypeNotFoundException("No storagetype with name " + storageType+ " could be found.");
		}
		return storageType.get();
	}
	private Storage findStorage(Family family, StorageType storageType) {
		Optional<Storage> storage = storageRepo.findByfamilyIdAndStorageType(family, storageType);
		if(storage.isEmpty()) {
			throw new StorageNotFoundException("Storage for family "+ family.getId()+ " with storageType "+storageType +" could not be found");
		}
		return storage.get();
	}
	private ItemCount findItemCount(Storage storage, Item item) {
		Optional<ItemCount> itemCount = itemCountRepo.findBystorageIdAndItemId(storage, item);
		if (itemCount.isEmpty()) {
			throw new ItemCountNotFoundException("Item with qrCode "+item.getQrCode()+" does not exist in "+ storage.getStorageType().getName());
		}
		return itemCount.get();
	}
}
