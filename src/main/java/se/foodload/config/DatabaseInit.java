package se.foodload.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import se.foodload.domain.Item;
import se.foodload.domain.ItemCategory;
import se.foodload.domain.StorageType;
import se.foodload.enums.StorageTypeEnums;
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
	/**
	 * Inits database with reused data.
	 * @param storageTypeRepo <code>StorageTypeRepository</code>
	 * @return a <code>CommandLineRunner</code> for init.
	 */
	@Bean
	CommandLineRunner initializeDatabase(StorageTypeRepository storageTypeRepo) {
		return args -> {
			if(storageTypeRepo.findByName(PANTRY).isEmpty()) {
				System.out.println("PANTRY EMPTY");
				StorageType pantry = new StorageType(PANTRY);
				storageTypeRepo.save(pantry);
			}
			if(storageTypeRepo.findByName(FREEZER).isEmpty()) {
				StorageType freezer = new StorageType(FREEZER);
				storageTypeRepo.save(freezer);
			}
			if(storageTypeRepo.findByName(FRIDGE).isEmpty()) {
				StorageType fridge = new StorageType(FRIDGE);
				storageTypeRepo.save(fridge);
			}
			if(itemCategoryRepo.findByName("Mejeri").isEmpty()) { // fixa standard kategorier som alla prods ska in i? ( enum)
				ItemCategory mejeri = new ItemCategory("Mejeri");
				itemCategoryRepo.save(mejeri);
			}
			if(itemRepo.findByName("Laktosf eko standardmjölkdryck 3,0%").isEmpty()) {
				ItemCategory mejeri = itemCategoryRepo.findByName("Mejeri").get();
				Item mellanEkoMjölk = new Item("Ekologisk färsk mellanmjölk 1,5%", "Arla", "07310865062024",mejeri);
				Item mellanMjölk = new Item("Laktosf eko standardmjölkdryck 3,0%", "Arla", "07310865875020", mejeri );
				itemRepo.save(mellanEkoMjölk);
				itemRepo.save(mellanMjölk);
			}
			
		};		
	}
}
