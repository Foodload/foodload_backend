package se.foodload.auth.filters;

import java.io.IOException;

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
public class FirebaseFilter extends OncePerRequestFilter{
	private final String AUTH_HEADER = FilterEnums.AUTH.getHeader(); 
	private final String BEARER_START = FilterEnums.BEARER.getHeader();
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
	
		final String requestHeader = request.getHeader(AUTH_HEADER);
		String authToken = null;
		FirebaseToken token = null;
		//Parsa fram token.
		if (requestHeader != null && requestHeader.startsWith(BEARER_START)) {
			authToken = requestHeader.substring(BEARER_START.length());

		} else {
			logger.warn("Jwt Token does not begin with Bearer String"); // FIXA error som throwas och visar error för client
		}
		//Ta fram token med info från firebase.
		try {
			token= FirebaseAuth.getInstance().verifyIdToken(authToken);
		}catch(FirebaseAuthException e) {
			logger.error("FireBase Exception: "+e.getLocalizedMessage()); // FIXA error som throwas och visar error för client
		}
		if(token != null) {
			ClientDTO clientDTO = new ClientDTO();
			clientDTO.setEmail(token.getEmail());
			clientDTO.setFirebaseId(token.getUid());
			
			clientDTO.setUsername(token.getName());
			
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    clientDTO, token, null);
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		filterChain.doFilter(request, response);
	}

}
