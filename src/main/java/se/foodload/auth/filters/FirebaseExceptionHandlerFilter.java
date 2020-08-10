package se.foodload.auth.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;


/**
 * A filter to handle exceptions thrown in the
 * <code>FirbaseFilter</code>.
 */
@Component
public class FirebaseExceptionHandlerFilter extends OncePerRequestFilter {

	/**
	 * Try-catch of the exceptions thrown by the <code>JwtRequestFilter</code>.
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (IllegalArgumentException exc) {
			logger.error(exc.getMessage());
			sendErrorResponse(HttpStatus.BAD_REQUEST, exc.getMessage(), response);
		} 
	}

	private void sendErrorResponse(HttpStatus httpStatus, String msg, HttpServletResponse response)
			throws JsonProcessingException, IOException {
		response.setStatus(httpStatus.value());
		ErrorResponse errorResponse = new ErrorResponse(httpStatus.getReasonPhrase(), msg);
		response.setContentType("application/json");
		response.getWriter().write(convertToJson(errorResponse));
	}

	private String convertToJson(Object object) throws JsonProcessingException {
		if (object == null)
			return null;
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(object);
	}

	private class ErrorResponse {
		private String httpStatus;
		private String msg;

		private ErrorResponse(String httpStatus, String msg) {
			this.httpStatus = httpStatus;
			this.msg = msg;
		}

		@SuppressWarnings("unused")
		public String getHttpStatus() {
			return this.httpStatus;
		}

		@SuppressWarnings("unused")
		public String getMsg() {
			return this.msg;
		}
	}

}
