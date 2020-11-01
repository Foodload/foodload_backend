package se.foodload.presentation.models;

import lombok.Data;
import se.foodload.domain.Item;

@Data
public class ItemResponse {
	private long itemCountId;
	private String qrCode;
	private String name;
	private String brand;
	private int amount;
	private String storageType;

	public ItemResponse() {

	}

	public ItemResponse(long id, Item item, int amount, String storage) {
		this.itemCountId = id;
		this.qrCode = item.getQrCode();
		this.name = item.getName();
		this.brand = item.getBrand();
		this.amount = amount;
		this.storageType = storage;
	}

	public ItemResponse(long id, Item item, int amount) {
		this.itemCountId = id;
		this.qrCode = item.getQrCode();
		this.name = item.getName();
		this.brand = item.getBrand();
		this.amount = amount;
	}
}
