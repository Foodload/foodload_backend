package se.foodload.enums;

public enum ErrorEnums {
	CLIENT_NOT_FOUND("No client could be found."),
	FAMILY_INVITE_NOT_FOUND("No family invite could be found."),
	FAMILY_NOT_FOUND("No family could be found."),
	ITEM_COUNT_NOT_FOUND("No item count could be found."),
	STORAGE_TYPE_NOT_FOUND("No storage type could be found."),
	ITEM_QR_NOT_FOUND("No item could be found for the given QR."),
	ITEM_NAME_NOT_FOUND("No item could be found for the given name."),
	ITEM_MOVE_CONFLICT("The item ammount does not match, update conflict");

	private final String errorMsg;

	ErrorEnums(final String errorMsg) {
		this.errorMsg = errorMsg;
	}

	@Override
	public String toString() {
		return errorMsg;
	}
}
