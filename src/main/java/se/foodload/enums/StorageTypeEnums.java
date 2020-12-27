package se.foodload.enums;

public enum StorageTypeEnums {
	FREEZER("Freezer"),
	FRIDGE("Fridge"),
	PANTRY("Pantry");

	private final String header;

	StorageTypeEnums(final String header) {
		this.header = header;
	}

	@Override
	public String toString() {
		return header;
	}
}
