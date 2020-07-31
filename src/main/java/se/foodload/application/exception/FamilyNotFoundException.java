package se.foodload.application.exception;

public class FamilyNotFoundException extends RuntimeException{
	private final int errorCode = 3;
	
	public FamilyNotFoundException(String msg){
		super(msg);
	}
	/**
	 * @return the exception code.
	 */
	public int getErrorCode() {
		return this.errorCode;
	}
}
