package se.foodload.application.exception;

public class StorageTypeNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int errorCode = 5;

	public StorageTypeNotFoundException(String msg) {
		super(msg);
	}

	public int getErrorCode() {
		return this.errorCode;
	}

}
