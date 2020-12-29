package se.foodload.application.exception;

public class NotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int errorCode = 1;
	private Object object;

	public NotFoundException(String msg, Object object) {
		super(msg);
		this.object = object;
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
	public Object getObject() {
		return this.object;
	}

}
