package se.foodload.application.exception;

public class ItemNotFoundException extends RuntimeException{
	private final int errorCode= 4;
	
	public ItemNotFoundException(String msg) {
		super(msg);
	}
	
	public int getErrorCode() {
		return this.errorCode;
	}

}
