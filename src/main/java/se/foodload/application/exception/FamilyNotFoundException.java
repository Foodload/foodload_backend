package se.foodload.application.exception;

public class FamilyNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int errorCode = 3;

	public FamilyNotFoundException(String msg) {
		super(msg);
	}

	/**
	 * @return the exception code.
	 */
	public int getErrorCode() {
		return this.errorCode;
	}
}
