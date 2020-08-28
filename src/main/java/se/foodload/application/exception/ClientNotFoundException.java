package se.foodload.application.exception;

public class ClientNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int errorCode = 1;

	public ClientNotFoundException(String msg) {
		super(msg);
	}

	/**
	 * @return the exception code.
	 */
	public int getErrorCode() {
		return this.errorCode;
	}
}
