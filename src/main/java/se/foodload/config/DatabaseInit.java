package se.foodload.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import se.foodload.domain.Item;
import se.foodload.domain.ItemCategory;
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

	/**
	 * Inits database with reused data.
	 * 
	 * @param storageTypeRepo <code>StorageTypeRepository</code>
	 * @return a <code>CommandLineRunner</code> for init.
	 */
	@Bean
	CommandLineRunner initializeDatabase(StorageTypeRepository storageTypeRepo, RedisMessagePublisher redisMessagePublisher) {
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
		
			if (itemRepo.findByName("Laktosf eko standardmjölkdryck 3,0%").isEmpty()) { 
				Item mellanEkoMjölk = new Item("Ekologisk färsk mellanmjölk 1,5%", "Arla", "7310865062024");
				Item mellanMjölk = new Item("Laktosf eko standardmjölkdryck 3,0%", "Arla", "7310865875020");
				itemRepo.save(mellanEkoMjölk);
				itemRepo.save(mellanMjölk);
			}
		
			//Optional<Item> item = itemRepo.findByQrCode("07310865062024");
			Optional<Item> item = itemRepo.findByQrCode("7310865062024");
			redisMessagePublisher.publishItem(true, item.get(), "1483982", 1, 2 );
		
			redisMessagePublisher.publishChangeFamily("1234", 1234, 3211);
			redisMessagePublisher.publishFamilyInvite("12345", 1234);
		
		};
	}
}
