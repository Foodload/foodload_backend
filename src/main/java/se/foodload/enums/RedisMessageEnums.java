package se.foodload.enums;

public enum RedisMessageEnums {
	UPDATE_ITEM("update_item"), FAMILY_INVITE("family_invite"), CHANGE_FAMILY("change_family");

	private String messageType;

	RedisMessageEnums(String messageType) {
		this.messageType = messageType;
	}

	public String getMessageType() {
		return this.messageType;
	}
}
