package se.foodload.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.foodload.application.Interfaces.IStorageService;
import se.foodload.domain.Family;
import se.foodload.domain.Item;
import se.foodload.domain.Storage;
import se.foodload.domain.StorageType;
import se.foodload.enums.StorageTypeEnums;
import se.foodload.repository.StorageRepository;
import se.foodload.repository.StorageTypeRepository;

@Service
public class StorageService implements IStorageService{
	@Autowired
	StorageTypeRepository storageTypeRep;
	@Autowired
	StorageRepository storageRepo;
	
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

	public void initStorages(Family family) {
		createStorage(storageTypeRep.findByName(PANTRY).get(), family);
		createStorage(storageTypeRep.findByName(FREEZER).get(), family);
		createStorage(storageTypeRep.findByName(FRIDGE).get(), family);
	}
	
	@Override
	public List<Storage> getStorages(Family family) {
		List storages = storageRepo.findByfamilyId(family).get();
		if(storages == null) {
			//throw errors Family not found.
		}
		return storages;
		
	}

	

	
	
}
