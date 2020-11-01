package se.foodload.redis;

import lombok.Data;

@Data
public class RedisItemUpdate {
	private String messageType;
	private long itemCountId;
	private String userId;
	private long familyId;
	private String name;
	private String brand;
	private String qrCode;
	private String storageType;
	private int amount;

	public RedisItemUpdate(long itemCountId, String messageType, String clientId, long familyId, String name,
			String brand, String qrCode, int amount, String storageType) {
		this.messageType = messageType;
		this.itemCountId = itemCountId;
		this.userId = clientId;
		this.familyId = familyId;
		this.name = name;
		this.brand = brand;
		this.qrCode = qrCode;
		this.amount = amount;
		this.storageType = storageType;
	}

	public RedisItemUpdate() {

	}
}
