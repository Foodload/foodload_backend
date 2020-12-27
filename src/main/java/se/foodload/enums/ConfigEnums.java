package se.foodload.enums;

public enum ConfigEnums {
	SERVICE_ACCOUNT_JSON("SERVICE_ACCOUNT_JSON"),
	MESSAGE_WHITE_SPACE("00a0"),
	CHANNEL_TOPIC("PublishItem");

	private final String config;

	ConfigEnums(final String config) {
		this.config = config;
	}

	@Override
	public String toString() {
		return config;
	}
}
