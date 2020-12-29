package se.foodload.presentation.error;

/**
 * Returns an error response to the client.
 *
 */
public class ErrorResponse {
	private String error;
	private String message;
	private int code;
	private Object object;

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

	public ErrorResponse(String reasonPhrase, String message2, int errorCode, Object object) {
		this.error = error;
		this.message = message;
		this.code = code;
		this.object = object;
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
	public Object getObject() {
		return this.object;
	}
}