package se.foodload.application.exception;

public class NotFoundException extends RuntimeException {

	private final static int errorCode = 1;
	private Object errorObject;

	public NotFoundException(String msg, Object errorObject) {
		super(msg);
		this.errorObject = errorObject;
	}

	public NotFoundException(String msg) {
		super(msg);
	}

	/**
	 * @return the exception code.
	 */
	public int getErrorCode() {
		return this.errorCode;
	}

	/**
	 * @return the object code.
	 */
	public Object getErrorObject() {
		return this.errorObject;
	}

}
