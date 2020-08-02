package se.foodload.redis;

import lombok.Data;

@Data
public class PublishItem {
	private boolean operation; 
	private String clientId;
	private long familyId;
	private String name;
	private String brand;
	private String qrCode;
	private int ammount; 

	public PublishItem(boolean operation, String clientId, long familyId, String name, String brand, String qrCode, int ammount) {
		this.operation = operation;
		this.clientId = clientId;
		this.familyId = familyId;
		this.name = name;
		this.brand = brand;
		this.qrCode = qrCode;
		this.ammount = ammount;
	}
	public PublishItem() {
		
	}
}
