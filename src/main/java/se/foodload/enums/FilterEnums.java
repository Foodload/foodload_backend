package se.foodload.enums;

public enum FilterEnums {

	AUTHORIZATION("Authorization"),
	BEARER("Bearer ");

	private final String text;

	FilterEnums(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}

}
