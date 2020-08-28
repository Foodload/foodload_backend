package se.foodload.application.exception;

public class StorageNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int errorCode = 7;

	public StorageNotFoundException(String msg) {
		super(msg);
	}

	/**
	 * @return the exception code.
	 */
	public int getErrorCode() {
		return this.errorCode;
	}

}
