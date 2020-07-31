package se.foodload.application.exception;

public class StorageNotFoundException extends RuntimeException{
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
