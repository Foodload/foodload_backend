package se.foodload.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import se.foodload.domain.StorageType;
import se.foodload.enums.StorageTypeEnums;
import se.foodload.repository.StorageTypeRepository;

public class DatabaseInit {
	private static final String PANTRY = StorageTypeEnums.PANTRY.getStorageType();
	private static final String FREEZER = StorageTypeEnums.FREEZER.getStorageType();
	private static final String FRIDGE = StorageTypeEnums.FRIDGE.getStorageType();
	
	/**
	 * Inits database with reused data.
	 * @param storageTypeRepo <code>StorageTypeRepository</code>
	 * @return a <code>CommandLineRunner</code> for init.
	 */
	@Bean
	CommandLineRunner initializeDatabase(StorageTypeRepository storageTypeRepo) {
		return args -> {
			if(storageTypeRepo.findByName(PANTRY).isEmpty()) {
				StorageType Pantry = new StorageType(PANTRY);
			}
			if(storageTypeRepo.findByName(FREEZER).isEmpty()) {
				StorageType Pantry = new StorageType(FREEZER);
			}
			if(storageTypeRepo.findByName(FRIDGE).isEmpty()) {
				StorageType Pantry = new StorageType(FRIDGE);
			}
			
		};		
	}
}
