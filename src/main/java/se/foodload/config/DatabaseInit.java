package se.foodload.config;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import se.foodload.domain.StorageType;
import se.foodload.enums.StorageTypeEnums;
import se.foodload.redis.RedisMessagePublisher;
import se.foodload.repository.ItemCategoryRepository;
import se.foodload.repository.ItemRepository;
import se.foodload.repository.StorageTypeRepository;

@Configuration
public class DatabaseInit {
	private static final String PANTRY = StorageTypeEnums.PANTRY.getStorageType();
	private static final String FREEZER = StorageTypeEnums.FREEZER.getStorageType();
	private static final String FRIDGE = StorageTypeEnums.FRIDGE.getStorageType();
	@Autowired
	StorageTypeRepository storageTypeRepo;
	@Autowired
	ItemCategoryRepository itemCategoryRepo;
	@Autowired
	ItemRepository itemRepo;
	@Autowired
	RedisMessagePublisher redisMessagePublisher;
	@PersistenceContext
	EntityManager em;

	/**
	 * Inits database with reused data.
	 * 
	 * @param storageTypeRepo <code>StorageTypeRepository</code>
	 * @return a <code>CommandLineRunner</code> for init.
	 */
	@Bean
	CommandLineRunner initializeDatabase(StorageTypeRepository storageTypeRepo,
			RedisMessagePublisher redisMessagePublisher, EntityManager em) {
		return args -> {
			if (storageTypeRepo.findByName(PANTRY).isEmpty()) {
				StorageType pantry = new StorageType(PANTRY);
				storageTypeRepo.save(pantry);
			}
			if (storageTypeRepo.findByName(FREEZER).isEmpty()) {
				StorageType freezer = new StorageType(FREEZER);
				storageTypeRepo.save(freezer);
			}
			if (storageTypeRepo.findByName(FRIDGE).isEmpty()) {
				StorageType fridge = new StorageType(FRIDGE);
				storageTypeRepo.save(fridge);
			}
		};
	}
}
