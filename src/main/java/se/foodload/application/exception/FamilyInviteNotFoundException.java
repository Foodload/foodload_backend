package se.foodload.application.exception;

public class FamilyInviteNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int errorCode = 2;

	public FamilyInviteNotFoundException(String msg) {
		super(msg);

	}

	/**
	 * @return the exception code.
	 */
	public int getErrorCode() {
		return this.errorCode;
	}
}
