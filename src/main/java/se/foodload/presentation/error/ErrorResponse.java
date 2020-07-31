package se.foodload.presentation.error;
/**
 * Returns an error response to the client.
 *
 */
public class ErrorResponse {
	private String logRef;
	private String message;
	private int code;

	/**
	 * Creates an instance of <code>ErrorResponse</code>.
	 * 
	 * @param logRef  The error.
	 * @param message The error message.
	 */
	ErrorResponse(String logRef, String message) {
		this.logRef = logRef;
		this.message = message;
	}

	/**
	 * Creates an instance of <code>ErrorResponse</code>.
	 * 
	 * @param logRef The error.
	 * @param message The error message.
	 * @param code The error code.
	 */
	ErrorResponse(String logRef, String message, int code) {
		this.logRef = logRef;
		this.message = message;
		this.code = code;
	}

	/**
	 * @return the error.
	 */
	public String getLogRef() {
		return this.logRef;
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
}