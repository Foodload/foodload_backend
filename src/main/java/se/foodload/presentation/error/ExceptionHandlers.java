package se.foodload.presentation.error;

import java.util.List;

import javax.servlet.ServletException;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import se.foodload.application.exception.ConflictException;
import se.foodload.application.exception.InsertionException;
import se.foodload.application.exception.NotFoundException;

@ControllerAdvice
@ResponseBody
public class ExceptionHandlers {
	private final String INVALID_METHOD_ARGUMENTS = "Invalid method arguments";
	private final String METHOD_ARGUMENT_TYPE_MISMATCH = "The type of the given arguments are wrong";


	/**
	 * Handles <code>InsertionException</code>.
	 *
	 * @param exc The exception thrown, caused by a failed insertion.
	 * @return the <code>ErrorResponse</code>.
	 */
	@ExceptionHandler(InsertionException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	ErrorResponse InsertionException(InsertionException exc) {
		return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), exc.getMessage());
	}

	/**
	 * Handles <code>IllegalArgumentException</code>.
	 *
	 * @param exc The exception thrown, caused by an illegal argument that the application does not accept to go further with.
	 * @return the <code>ErrorResponse</code>.
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	ErrorResponse IllegalArgumentException(IllegalArgumentException exc) {
		return new ErrorResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), exc.getMessage());
	}


	/**
	 * Handles <code>ConflictException</code>.
	 * 
	 * @param exc The exception thrown, caused by a conflict.
	 * @return the <code>ErrorResponse</code>.
	 */
	@ExceptionHandler(ConflictException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	ErrorResponse ConflictException(ConflictException exc) {
		return new ErrorResponse(HttpStatus.CONFLICT.getReasonPhrase(), exc.getMessage(), exc.getErrorCode(),
				exc.getObject());
	}

	/**
	 * Handles <code>ClientNotFoundException</code>.
	 * 
	 * @param exc The exception thrown, caused by failed search.
	 * @return the <code>ErrorResponse</code>.
	 */
	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	ErrorResponse NotFoundException(NotFoundException exc) {
		return new ErrorResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), exc.getMessage(), exc.getErrorCode());
	}

	@ExceptionHandler(ServletException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	ErrorResponse noServletException(ServletException exc) {
		return new ErrorResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), exc.getMessage());

	}

	/**
	 * Handler for invalid method arguments. For example, missing fields in
	 * <code>Application</code>s.
	 * 
	 * @param exc The <code>MethodArgumentNotValidException</code>.
	 * @return a list over all the violations. See {@link ViolationResponse}.
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	ViolationResponse constraintViolationExceptionHandler(MethodArgumentNotValidException exc) {
		ViolationResponse vr = new ViolationResponse(HttpStatus.BAD_REQUEST.getReasonPhrase(),
				INVALID_METHOD_ARGUMENTS);
		List<Violation> violations = vr.getViolations();
		exc.getBindingResult().getFieldErrors().forEach(fieldError -> {
			violations.add(new Violation(fieldError.getField(), fieldError.getDefaultMessage()));
		});
		return vr;
	}

	/**
	 * Handles <code>NoHandlerException</code>s.
	 * 
	 * @param exc The exception.
	 * @return the <code>ErrorResponse</code>.
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	ErrorResponse noHandlerFoundExceptionHandler(NoHandlerFoundException exc) {

		return new ErrorResponse(HttpStatus.NOT_FOUND.getReasonPhrase(), exc.getMessage());
	}

	/**
	 * Handles <code>MissingServletRequestParameterException</code>s.
	 * 
	 * @param exc The exception.
	 * @return the <code>ErrorResponse</code>.
	 */
	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	ErrorResponse missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException exc) {

		return new ErrorResponse(HttpStatus.BAD_REQUEST.getReasonPhrase(), exc.getMessage());
	}

	/**
	 * Handler for not supported methods.
	 * 
	 * @param exc The <code>HttpRequestMethodNotSupportedException</code>.
	 * @return the <code>ErrorResponse</code>.
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	ErrorResponse methodNotAllowedHandler(HttpRequestMethodNotSupportedException exc) {

		return new ErrorResponse(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase(), exc.getMessage());
	}

	/**
	 * Handler for method argument type mismatches.
	 * 
	 * @param exc The <code>MethodArgumentTypeMismatchException</code>.
	 * @return the <code>ErrorResponse</code> with the exception message with the
	 *         expected type.
	 */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	ErrorResponse methodArgumentTypeMismatchHandler(MethodArgumentTypeMismatchException exc) {

		return new ErrorResponse(HttpStatus.BAD_REQUEST.getReasonPhrase(),
				METHOD_ARGUMENT_TYPE_MISMATCH + ", expected: " + exc.getRequiredType().getSimpleName());
	}

	/**
	 * Handler for bad HTTP messages received.
	 * 
	 * @param exc The <code>HttpMessageNotReadableException</code>.
	 * @return a very sad face.
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	ErrorResponse messageNotReadableExceptionHandler(HttpMessageNotReadableException exc) {

		return new ErrorResponse(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Could not read the message!:(");
	}

	/**
	 * Handles the exceptions that are not handled by any other exception handler.
	 * 
	 * @param exc the exception to be handled.
	 * @return the <code>ErrorResponse</code>.
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	ErrorResponse generalExceptionHandler(Exception exc) {
		exc.printStackTrace();

		return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), exc.getMessage());
	}

}
