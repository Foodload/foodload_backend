package se.foodload.application;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.foodload.application.Interfaces.IItemService;
import se.foodload.domain.Family;
import se.foodload.domain.Item;
import se.foodload.domain.ItemCount;
import se.foodload.domain.Storage;
import se.foodload.domain.StorageType;
import se.foodload.repository.ItemCountRepository;
import se.foodload.repository.ItemRepository;
import se.foodload.repository.StorageTypeRepository;

@Service
public class ItemService implements IItemService {
	@Autowired
	ItemRepository itemRepo;
	@Autowired
	StorageService storageService;
	@Autowired
	ItemCountRepository itemCountRepo;

	@Override
	public Item findItem(String qrCode) {
	
		Optional<Item> item = itemRepo.findByQrCode(qrCode);
		if (item.isEmpty()) {
			// Throw item not found error.
		}
		return item.get();

	}

	@Override
	public void addItem(Family family, String qrCode, String storageType, int ammount) {
		List<Storage> storages = storageService.getStorages(family);
		Optional<Item> item = itemRepo.findByQrCode(qrCode);
		if (item.isEmpty()) {
			// throw error item not found.
		}
		Item foundItem = item.get();
		System.out.println(foundItem);
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
				// throw error no storagetype found.
			}
		}
	}

	@Override
	public void deleteItem(Family family, String qrCode, String storageType) {
		List<Storage> storages = storageService.getStorages(family);
		Optional<Item> item = itemRepo.findByQrCode(qrCode);
		if (item.isEmpty()) {
			// throw error item not found.
		}
		Item foundItem = item.get();
		for (Storage storage : storages) {
			if (storage.getStorageType().getName().contentEquals(storageType)) {
				Optional<ItemCount> itemCount = itemCountRepo.findBystorageIdAndItemId(storage, foundItem);
				if (itemCount.isEmpty()) {
					// THROW ERROR ITEM IS NOT IN STORAGE TO BEGIN WITH.
				}
				itemCount.get().removeItemCount();
				itemCountRepo.save(itemCount.get());
			} else {
				// throw error Storagetype not found.
			}
		}

	}

	public void alterStroage(Family family, String qrCode, String storageType, String newStorageType) {
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
					// THROW ERROR ITEM IS NOT IN STORAGE TO BEGIN WITH.
				}
				itemCount.get().setStorageId(newStorage);
				itemCountRepo.save(itemCount.get());
			} else {
				// throw error Storagetype not found.
			}

		}

	}
	
	
}
