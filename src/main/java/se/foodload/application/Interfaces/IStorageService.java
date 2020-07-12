package se.foodload.application.Interfaces;

import java.util.List;

import se.foodload.domain.Family;

import se.foodload.domain.Storage;
import se.foodload.domain.StorageType;

public interface IStorageService {
	/**
	 * Registers a new Storage in the database.	
	 * @param <code>StorageType</code> The storage type to register.
	 * @return <code>Storage</code>
	 */
	public Storage createStorage(StorageType storageType, Family family);
	
	public void initStorages(Family family);

	public List<Storage> getStorages(Family family);
	
	public Storage getFreezer(Family family);
	public Storage getFridge(Family family);
	public Storage getPantry(Family family);
	
}
