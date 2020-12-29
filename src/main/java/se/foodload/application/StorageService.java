package se.foodload.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.foodload.application.interfaces.IStorageService;
import se.foodload.application.exception.NotFoundException;
import se.foodload.domain.Family;
import se.foodload.domain.ItemCount;
import se.foodload.domain.StorageType;
import se.foodload.enums.ErrorEnums;
import se.foodload.enums.StorageTypeEnums;
import se.foodload.repository.ItemCountRepository;
import se.foodload.repository.StorageTypeRepository;

@Service
public class StorageService implements IStorageService {
	@Autowired
	ItemCountRepository itemCountRepo;
	@Autowired
	StorageTypeRepository storageTypeRepo;

	@Override
	public List<ItemCount> getFreezer(Family family) {

		Optional<StorageType> freezer = storageTypeRepo.findByName(StorageTypeEnums.FREEZER.toString());
		if (freezer.isEmpty()) {
			throw new NotFoundException(ErrorEnums.STORAGE_TYPE_NOT_FOUND.toString());
		}
		Optional<List<ItemCount>> freezerCount = itemCountRepo.findByStorageTypeAndFamilyId(freezer.get(), family);
		if (freezerCount.isEmpty()) {
			return new ArrayList<>();
		}
		return freezerCount.get();
	}

	@Override
	public List<ItemCount> getFridge(Family family) {
		Optional<StorageType> fridge = storageTypeRepo.findByName(StorageTypeEnums.FRIDGE.toString());
		if (fridge.isEmpty()) {
			throw new NotFoundException(ErrorEnums.STORAGE_TYPE_NOT_FOUND.toString());
		}

		Optional<List<ItemCount>> fridgeCount = itemCountRepo.findByStorageTypeAndFamilyId(fridge.get(), family);
		if (fridgeCount.isEmpty()) {
			return new ArrayList<>();
		}

		return fridgeCount.get();

	}

	@Override
	public List<ItemCount> getPantry(Family family) {
		Optional<StorageType> pantry = storageTypeRepo.findByName(StorageTypeEnums.PANTRY.toString());
		if (pantry.isEmpty()) {
			throw new NotFoundException(ErrorEnums.STORAGE_TYPE_NOT_FOUND.toString());
		}
		Optional<List<ItemCount>> pantryCount = itemCountRepo.findByStorageTypeAndFamilyId(pantry.get(), family);
		if (pantryCount.isEmpty()) {
			return new ArrayList<>();
		}
		return pantryCount.get();
	}

	@Override
	public List<ItemCount> getItemCounts(Family family) {
		Optional<List<ItemCount>> storages = itemCountRepo.findByfamilyId(family);
		if (storages.isEmpty()) {
			return new ArrayList<>();
		}
		return storages.get();
	}
}
