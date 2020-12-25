package se.foodload.application.interfaces;

import java.util.List;

import se.foodload.domain.Family;
import se.foodload.domain.Item;
import se.foodload.domain.ItemCount;

public interface IItemService {

	public Item findItem(String name);

	public void addItem(String clientId, Family family, String qrCode, String storageType, int ammount);

	public void removeItem(String clientId, Family family, String qrCode, String storageType, int ammount);

	public void incrementItem(String clientId, long itemCountId, long familyId);

	public void decrementItem(String clientId, long itemCountId, long familyId);

	public List<ItemCount> getAllItemCounts(Family family);

	public List<Item> findItemPattern(String pattern, int start);

	public void alterStorage(Family family, String qrCode, String storageName, String newStorageName);

	public int moveItemTo(long familyId, long itemCountId, String clientId, String destStorageType, int moveAmount, int oldAmount);

	public int moveItemFrom(long familyId, long itemCountId, String clientId, String srcStorageType, int moveAmount, int oldAmount);

	public void deleteItem(long familyId, long itemCountId, String clientId, int amount);
}
