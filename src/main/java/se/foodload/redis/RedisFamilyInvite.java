package se.foodload.redis;

import lombok.Data;

@Data
public class RedisFamilyInvite {
	private String messageType;
	private String userId;
	private long familyId;

	public RedisFamilyInvite(String messageType, String clientId, long familyId) {
		this.messageType = messageType;
		this.userId = clientId;
		this.familyId = familyId;
	}

	public RedisFamilyInvite() {
	}
}
