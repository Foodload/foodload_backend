package se.foodload.presentation.models;

import lombok.Data;
import se.foodload.domain.Item;
@Data
public class ItemResponse {
	private String qrCode;
	private String name;
	private String brand;
	private int ammount;
	
	public ItemResponse() {
		
	}
	public ItemResponse(Item item, int ammount) {
		this.qrCode= item.getQrCode();
		this.name = item.getName();
		this.brand = item.getBrand();
		this.ammount = ammount;
		
	}
}
