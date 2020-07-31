package se.foodload.application.exception;

public class ItemCountNotFoundException extends RuntimeException {
	private final int errorCode = 6;
	
	public ItemCountNotFoundException(String msg) {
		super(msg);
	}
	public int getErrorCode() {
		return this.errorCode;
	}
}
