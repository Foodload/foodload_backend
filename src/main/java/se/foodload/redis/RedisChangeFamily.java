package se.foodload.redis;

import lombok.Data;

@Data
public class RedisChangeFamily {
	private String messageType;
	private String userId;
	private long familyId;
	private long oldFamilyId;
	public RedisChangeFamily(String messageType, String clientId, long familyId, long oldFamilyId) {
		this.messageType = messageType;
		this.userId = clientId;
		this.familyId = familyId;
		this.oldFamilyId = oldFamilyId;
		
	}
	public RedisChangeFamily() {}
	
}
