package se.foodload.application.exception;

public class ItemNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int errorCode = 4;

	public ItemNotFoundException(String msg) {
		super(msg);
	}

	public int getErrorCode() {
		return this.errorCode;
	}

}
