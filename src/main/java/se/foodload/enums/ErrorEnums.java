package se.foodload.enums;

public enum ErrorEnums {
	// CLIENT NOT FOUND ERRORS
	CLIENTNOTFOUND("No client could be found with email: "),
	// FAMILY INVITE ERRORS
	FAMILYINVITENOTFOUND("No Family invite could be found with id: "),

	// FAMILY NOT FOUND ERRORS
	FAMILYNOTFOUND("No family could be found with id: "), ITEMCOUNTNOTFOUNDSTORAGE("No item count for storagetype: "),

	// ITEMCOUNT ERRORS

	ITEMCOUNTNOTFOUNDID("ItemCount with id: "), ITEMCOUNTNOTFOUNDID2(" does not belong to familyId: "),
	ITEMCOUNTQFS("Item with qrCode "), ITEMCOUNTQSF2(" does not exist in "), ITEMCOUNTQSF3(" for family: "),
	ITEMCOUNTDOESNOTEXIST("No item counts exist for familyid: "),

	// STORAGE NOT FOUND ERRORS

	// STORAGENOTFOUNDFAMILY("No storage could be found for family: "),
	// STORAGENOTFOUNDFAMILY2(" with storageType "),

	// STORAGETYPE NOT FOUND ERRORS

	STORAGETYPENOTFOUND("No StorageType could be found by name: "),

	// ITEM NOT FOUND ERRORS

	ITEMNOTFOUND("Item could not be found with qrCode ");

	private String errorMsg;

	ErrorEnums(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getErrorMsg() {
		return this.errorMsg;
	}
}
