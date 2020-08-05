package se.foodload.redis;

import lombok.Data;

@Data
public class RedisItemUpdate {
	private String messageType;
	private boolean operation; 
	private String userId;
	private long familyId;
	private String name;
	private String brand;
	private String qrCode;
	private int ammount; 

	
	public RedisItemUpdate( String messageType, boolean operation, String clientId, long familyId, String name, String brand, String qrCode, int ammount) {
		this.messageType = messageType;
		this.operation = operation;
		this.userId = clientId;
		this.familyId = familyId;
		this.name = name;
		this.brand = brand;
		this.qrCode = qrCode;
		this.ammount = ammount;
	}

	public RedisItemUpdate() {
		
	}
}
