package se.foodload.application.Interfaces;

import se.foodload.domain.Family;
import se.foodload.domain.Item;

public interface IItemService {
	
	public Item findItem(String name);
	


	public void addItem(Family family, String qrCode);
	public void deleteItem(Family family, String qrCode);
}
