package se.foodload.enums;

public enum RedisMessageEnums {
	UPDATE_ITEM("update_item"),
	FAMILY_INVITE("family_invite"),
	CHANGE_FAMILY("change_family"),
	MOVE_ITEM("move_item"),
	DELETE_ITEM("delete_item");

	private final String messageType;

	RedisMessageEnums(final String messageType) {
		this.messageType = messageType;
	}

	@Override
	public String toString() {
		return messageType;
	}
}
