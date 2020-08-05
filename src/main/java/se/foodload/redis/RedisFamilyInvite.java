package se.foodload.redis;

import lombok.Data;
import se.foodload.enums.RedisMessageEnums;

@Data
public class RedisFamilyInvite {
	private String messageType;
	private String userId;
	private long familyId;
	public RedisFamilyInvite(String messageType, String clientId, long familyId) {
		this.messageType = messageType;
		this.userId=clientId;
		this.familyId= familyId;
	}
	public RedisFamilyInvite() {}
}
