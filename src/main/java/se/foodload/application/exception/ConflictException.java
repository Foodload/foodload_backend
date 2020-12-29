package se.foodload.application.exception;

import se.foodload.application.exception.dto.ConflictDTO;

public class ConflictException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final int errorCode = 8;
	private Object object;

	public ConflictException(String msg, Object object) {
		super(msg);
		this.object = object;

	}

	/**
	 * @return the exception code.
	 */
	public int getErrorCode() {
		return this.errorCode;
	}

	public Object getObject() {
		return this.object;
	}
}
