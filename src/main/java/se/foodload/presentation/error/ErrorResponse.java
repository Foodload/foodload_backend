package se.foodload.presentation.error;

/**
 * Returns an error response to the client.
 *
 */
public class ErrorResponse {
	private final String error;
	private final String message;
	private int code;
	private Object errorObject;

	/**
	 * Creates an instance of <code>ErrorResponse</code>.
	 * 
	 * @param error   The error.
	 * @param message The error message.
	 */
	ErrorResponse(String error, String message) {
		this.error = error;
		this.message = message;
	}

	/**
	 * Creates an instance of <code>ErrorResponse</code>.
	 * 
	 * @param error   The error.
	 * @param message The error message.
	 * @param code    The error code.
	 */
	ErrorResponse(String error, String message, int code) {
		this.error = error;
		this.message = message;
		this.code = code;
	}

	public ErrorResponse(String error, String message, int code, Object errorObject) {
		this.error = error;
		this.message = message;
		this.code = code;
		this.errorObject = errorObject;
	}

	/**
	 * @return the error.
	 */
	public String getError() {
		return this.error;
	}

	/**
	 * @return the error message.
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * @return the error code.
	 */
	public int getCode() {
		return this.code;
	}

	/**
	 * @return the Object.
	 */
	public Object getErrorObject() {
		return this.errorObject;
	}
}