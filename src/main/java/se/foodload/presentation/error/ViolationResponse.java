package se.foodload.presentation.error;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds all the <code>Violation</code>s to be returned to the client.
 *
 */
public class ViolationResponse {
	private String logRef;
	private String message;
	private List<Violation> violations = new ArrayList<>();

	/**
	 * Creates an instance of <code>ViolationResponse</code>.
	 * 
	 * @param logRef  The error.
	 * @param message The error message.
	 */
	ViolationResponse(String logRef, String message) {
		this.logRef = logRef;
		this.message = message;
	}

	/**
	 * @return the list of <code>Violation</code>s.
	 */
	public List<Violation> getViolations() {
		return this.violations;
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
}