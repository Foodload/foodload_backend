package se.foodload.application.exception;

public class FamilyInviteNotFoundException extends RuntimeException{
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
