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
			if (storageTypeRepo.findByName(StorageTypeEnums.PANTRY.toString()).isEmpty()) {
				StorageType pantry = new StorageType(StorageTypeEnums.PANTRY.toString());
				storageTypeRepo.save(pantry);
			}
			if (storageTypeRepo.findByName(StorageTypeEnums.FREEZER.toString()).isEmpty()) {
				StorageType freezer = new StorageType(StorageTypeEnums.FREEZER.toString());
				storageTypeRepo.save(freezer);
			}
			if (storageTypeRepo.findByName(StorageTypeEnums.FRIDGE.toString()).isEmpty()) {
				StorageType fridge = new StorageType(StorageTypeEnums.FRIDGE.toString());
				storageTypeRepo.save(fridge);
			}
		};
	}
}
