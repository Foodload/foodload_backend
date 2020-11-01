package se.foodload.application.interfaces;

import java.util.List;

import se.foodload.domain.Family;
import se.foodload.domain.ItemCount;

public interface IStorageService {
	/**
	 * Registers a new Storage in the database.
	 * 
	 * @param <code>StorageType</code> The storage type to register.
	 * @return <code>Storage</code>
	 */
	// public Storage createStorage(StorageType storageType, Family family);

	// public void initStorages(Family family);

	// public List<Storage> getStorages(Family family);
	public List<ItemCount> getItemCounts(Family family);

	public List<ItemCount> getFreezer(Family family);

	public List<ItemCount> getFridge(Family family);

	public List<ItemCount> getPantry(Family family);

}
