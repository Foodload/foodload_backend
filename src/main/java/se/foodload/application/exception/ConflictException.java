package se.foodload.application.exception;

public class ConflictException extends RuntimeException {

	private static final int errorCode = 8;
	private Object errorObject;

	public ConflictException(String msg, Object errorObject) {
		super(msg);
		this.errorObject = errorObject;

	}

	public ConflictException(String msg) {
		super(msg);
	}

	/**
	 * @return the exception code.
	 */
	public int getErrorCode() {
		return errorCode;
	}

	public Object getObject() {
		return this.errorObject;
	}
}
