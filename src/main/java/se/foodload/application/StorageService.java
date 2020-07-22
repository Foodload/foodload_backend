package se.foodload.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

import se.foodload.application.Interfaces.IStorageService;
import se.foodload.domain.Family;
import se.foodload.domain.ItemCount;
import se.foodload.domain.Storage;
import se.foodload.domain.StorageType;
import se.foodload.enums.StorageTypeEnums;
import se.foodload.repository.ItemCountRepository;
import se.foodload.repository.StorageRepository;
import se.foodload.repository.StorageTypeRepository;

@Service
public class StorageService implements IStorageService{
	@Autowired
	StorageTypeRepository storageTypeRep;
	@Autowired
	StorageRepository storageRepo;
	@Autowired 
	ItemCountRepository itemCountRepo;
	private static final String PANTRY = StorageTypeEnums.PANTRY.getStorageType();
	private static final String FREEZER = StorageTypeEnums.FREEZER.getStorageType();
	private static final String FRIDGE = StorageTypeEnums.FRIDGE.getStorageType();
	
	/**
	 * Registers a new Storage in the database.	
	 * @param <code>StorageType</code> The storage type to register.
	 * @return <code>Storage</code>
	 */
	@Override
	public Storage createStorage(StorageType storageType, Family family) {
		Storage storage = new Storage(storageType, family);
		return storageRepo.save(storage);
		 
	}
	@Override
	public void initStorages(Family family) {
		createStorage(storageTypeRep.findByName(PANTRY).get(), family);
		createStorage(storageTypeRep.findByName(FREEZER).get(), family);
		createStorage(storageTypeRep.findByName(FRIDGE).get(), family);
	}
	
	@Override
	public List<Storage> getStorages(Family family) {
		List<Storage> storages = storageRepo.findByfamilyId(family).get();
		if(storages == null) {
			//throw errors Family not found.
		}
		return storages;
	}
	
	
	@Override
	public ItemCount getFreezer(Family family) {
		
		Storage freezer = getStorage(family, FREEZER);
		
		Optional<ItemCount> freezerCount = itemCountRepo.findBystorageId(freezer);
		if(freezerCount.isEmpty()) {
			//THROW ERRORS...
		}
		return freezerCount.get();
	}
	@Override
	public ItemCount getFridge(Family family) {
		
		Storage fridge= getStorage(family, FRIDGE);
		
		Optional<ItemCount> fridgeCount = itemCountRepo.findBystorageId(fridge);
		if(fridgeCount.isEmpty()) {
			//THROW ERRORS...
		}
		return fridgeCount.get();
		
	}
	@Override
	public ItemCount getPantry(Family family) {
		Storage pantry = getStorage(family, PANTRY);
		Optional<ItemCount> pantryCount = itemCountRepo.findBystorageId(pantry);
		if(pantryCount.isEmpty()) {
			//THROW ERRORS...
		}
		return pantryCount.get();
	}
	
	
	private Storage getStorage(Family family, String storageTypeString) {
		Optional<StorageType> storageType = storageTypeRep.findByName(storageTypeString);
		if(storageType.isEmpty()) {
			//THROW CANT FIND STORAGETYPE.
		}
		Optional<Storage> storage =storageRepo.findByfamilyIdAndStorageType(family, storageType.get());
		if(storage.isEmpty()) {
			//THROW CANT FIND STORAGE.
		}
		return storage.get();	
	}
	
	
}
