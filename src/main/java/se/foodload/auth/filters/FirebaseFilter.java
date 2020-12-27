package se.foodload.auth.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

import se.foodload.enums.FilterEnums;
import se.foodload.presentation.dto.ClientDTO;

@Component
public class FirebaseFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String requestHeader = request.getHeader(FilterEnums.AUTHORIZATION.toString());
		String authToken = null;
		FirebaseToken token = null;
		// Parsa fram token.
		if (requestHeader != null && requestHeader.startsWith(FilterEnums.BEARER.toString())) {
			authToken = requestHeader.substring(FilterEnums.BEARER.toString().length());
			// System.out.println(authToken);

		} else {
			logger.warn("Jwt Token does not begin with Bearer String"); // FIXA error som throwas och visar error för
																		// client
		}
		// Ta fram token med info från firebase.
		try {
			token = FirebaseAuth.getInstance().verifyIdToken(authToken);
		} catch (FirebaseAuthException e) {
			logger.error("FireBase Exception: " + e.getLocalizedMessage()); // FIXA error som throwas och visar error
																			// för client
		}
		if (token != null) {
			ClientDTO clientDTO = new ClientDTO();
			clientDTO.setEmail(token.getEmail());
			clientDTO.setFirebaseId(token.getUid());
			clientDTO.setUsername(token.getName());

			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(clientDTO,
					token, null);
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		filterChain.doFilter(request, response);
	}

}
