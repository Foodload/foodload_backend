package se.foodload.enums;

public enum ConfigEnums {
	SERVICECONFIG("SERVICE_ACCOUNT_JSON"), MESSAGEWHITESPACE("00a0"), CHANNELTOPIC("PublishItem");

	private String config;

	ConfigEnums(String config) {
		this.config = config;
	}

	public String getConfig() {
		return this.config;
	}
}
