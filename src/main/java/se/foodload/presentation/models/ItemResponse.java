package se.foodload.presentation.models;

import lombok.Data;
import se.foodload.domain.Item;

@Data
public class ItemResponse {
	private long id;
	private String qrCode;
	private String name;
	private String brand;
	private int amount;

	public ItemResponse() {

	}

	public ItemResponse(long id, Item item, int amount) {
		this.id = id;
		this.qrCode = item.getQrCode();
		this.name = item.getName();
		this.brand = item.getBrand();
		this.amount = amount;

	}
}
