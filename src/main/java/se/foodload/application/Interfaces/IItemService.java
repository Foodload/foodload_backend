package se.foodload.application.Interfaces;

import se.foodload.domain.Family;
import se.foodload.domain.Item;
import se.foodload.domain.StorageType;

public interface IItemService {

	public Item findItem(String name);

	public void addItem(String clientId, Family family, String qrCode, String storageType, int ammount);

	public void deleteItem(String clientId, Family family, String qrCode, String storageType, int ammount);
}
