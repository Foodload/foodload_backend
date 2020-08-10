package se.foodload.redis;

import lombok.Data;

@Data
public class RedisItemUpdate {
	private String messageType;
	private String userId;
	private long familyId;
	private String name;
	private String brand;
	private String qrCode;
	private int amount; 

	
	public RedisItemUpdate( String messageType, String clientId, long familyId, String name, String brand, String qrCode, int amount) {
		this.messageType = messageType;
		this.userId = clientId;
		this.familyId = familyId;
		this.name = name;
		this.brand = brand;
		this.qrCode = qrCode;
		this.amount = amount;
	}

	public RedisItemUpdate() {
		
	}
}
