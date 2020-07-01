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
	public Item findItem(String name) {
		Optional<Item> item = itemRepo.findByName(name);
		if(item.isEmpty()) {
			//Throw item not found error.
		}
		return item.get();
		
	}
	@Override
	public void addItem(Family family, String qrCode) {
		List<Storage> storages = storageService.getStorages(family);
		Optional<Item> item = itemRepo.findByqrCode(qrCode);
		if(item.isEmpty()) {
			//throw error item not found.
		}
		Item foundItem = item.get();
		for(Storage storage : storages) {
			if(storage.getStorageType() == foundItem.getStorageType()) {
				Optional<ItemCount> itemCount = itemCountRepo.findBystorageIdAndItemId(storage,foundItem);
				if(itemCount.isEmpty()) {
					ItemCount newItemCount = new ItemCount(storage, foundItem);
					itemCountRepo.save(newItemCount);
				}
				itemCount.get().addItemCount();
				itemCountRepo.save(itemCount.get());
				}
			}
		}
	}


