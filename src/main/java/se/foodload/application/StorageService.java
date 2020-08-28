package se.foodload.application;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.foodload.application.Interfaces.IStorageService;
import se.foodload.application.exception.ItemCountNotFoundException;
import se.foodload.application.exception.StorageNotFoundException;
import se.foodload.application.exception.StorageTypeNotFoundException;
import se.foodload.domain.Family;
import se.foodload.domain.ItemCount;
import se.foodload.domain.Storage;
import se.foodload.domain.StorageType;
import se.foodload.enums.ErrorEnums;
import se.foodload.enums.StorageTypeEnums;
import se.foodload.repository.ItemCountRepository;
import se.foodload.repository.StorageRepository;
import se.foodload.repository.StorageTypeRepository;

@Service
public class StorageService implements IStorageService {
	@Autowired
	StorageTypeRepository storageTypeRep;
	@Autowired
	StorageRepository storageRepo;
	@Autowired
	ItemCountRepository itemCountRepo;
	private static final String PANTRY = StorageTypeEnums.PANTRY.getStorageType();
	private static final String FREEZER = StorageTypeEnums.FREEZER.getStorageType();
	private static final String FRIDGE = StorageTypeEnums.FRIDGE.getStorageType();
	private final String ITEM_COUNT_NOT_FOUND_STORAGE = ErrorEnums.ITEMCOUNTNOTFOUNDSTORAGE.getErrorMsg();
	private final String STORAGE_NOT_FOUND_FAMILY = ErrorEnums.STORAGENOTFOUNDFAMILY.getErrorMsg();
	private final String STORAGE_NOT_FOUND_FAMILY_2 = ErrorEnums.STORAGENOTFOUNDFAMILY2.getErrorMsg();
	private final String STORAGE_TYPE_NOT_FOUND = ErrorEnums.STORAGETYPENOTFOUND.getErrorMsg();

	/**
	 * Registers a new Storage in the database.
	 * 
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
		if (storages == null) {
			throw new StorageNotFoundException(STORAGE_NOT_FOUND_FAMILY + family.getName());
		}
		return storages;
	}

	@Override
	public List<ItemCount> getFreezer(Family family) {

		Storage freezer = getStorage(family, FREEZER);

		Optional<List<ItemCount>> freezerCount = itemCountRepo.findByStorage(freezer);
		if (freezerCount.isEmpty()) {
			throw new ItemCountNotFoundException(ITEM_COUNT_NOT_FOUND_STORAGE + FREEZER);
		}
		return freezerCount.get();
	}

	@Override
	public List<ItemCount> getFridge(Family family) {

		Storage fridge = getStorage(family, FRIDGE);

		Optional<List<ItemCount>> fridgeCount = itemCountRepo.findByStorage(fridge);
		if (fridgeCount.isEmpty()) {
			throw new ItemCountNotFoundException(ITEM_COUNT_NOT_FOUND_STORAGE + FRIDGE);
		}

		return fridgeCount.get();

	}

	@Override
	public List<ItemCount> getPantry(Family family) {
		Storage pantry = getStorage(family, PANTRY);
		Optional<List<ItemCount>> pantryCount = itemCountRepo.findByStorage(pantry);
		if (pantryCount.isEmpty()) {
			throw new ItemCountNotFoundException(ITEM_COUNT_NOT_FOUND_STORAGE + PANTRY);
		}
		return pantryCount.get();
	}

	private Storage getStorage(Family family, String storageTypeString) {
		Optional<StorageType> storageType = storageTypeRep.findByName(storageTypeString);
		if (storageType.isEmpty()) {
			throw new StorageTypeNotFoundException(STORAGE_TYPE_NOT_FOUND + storageTypeString);
		}
		Optional<Storage> storage = storageRepo.findByFamilyIdAndStorageType(family, storageType.get());
		if (storage.isEmpty()) {
			throw new StorageNotFoundException(
					STORAGE_NOT_FOUND_FAMILY + family.getName() + STORAGE_NOT_FOUND_FAMILY_2 + storageTypeString);
		}
		return storage.get();
	}

}
