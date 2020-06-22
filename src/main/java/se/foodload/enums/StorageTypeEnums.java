package se.foodload.enums;

public enum StorageTypeEnums {
	FREEZER("Freezer"),
	FRIDGE("Fridge "),
	PANTRY("Pantry");
	
	private String header;
	StorageTypeEnums(String header)
	{
		this.header = header;
	}
	public String getStorageType() {
		return this.header;
	}
}
