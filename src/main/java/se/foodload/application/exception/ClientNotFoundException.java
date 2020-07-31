package se.foodload.application.exception;

public class ClientNotFoundException extends RuntimeException{
	private final int errorCode =1; 
	
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

