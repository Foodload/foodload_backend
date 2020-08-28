package se.foodload.enums;

public enum FilterEnums {

	AUTH("Authorization"), BEARER("Bearer ");

	private String header;

	FilterEnums(String header) {
		this.header = header;
	}

	public String getHeader() {
		return this.header;
	}

}
