package se.foodload.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import se.foodload.domain.Client;
import se.foodload.domain.Family;

/**
 * Component that helps to handle JSON Web Tokens (JWTs) such as: generating new JWTs,
 * validation, and extraction.
 *
 */
@Component
public class JwtTokenUtil {
	public static final long JWT_EXPIRATION_TIME = 5 * 60 * 60 * 1000;

	@Value("${jwt.secret}")
	private String secret;

	/**
	 * Extracts clientId from a token.
	 * 
	 * @param token The JWT.
	 * @return the clientId from the token.
	 */
	public String getTokenClientId(String token) {
		return exctractTokenClaim(token, Claims::getSubject);
	}

	/**
	 * Extracts expiration date from token.
	 * 
	 * @param token The JWT.
	 * @return a <code>Date</code> with the expiration date.
	 */
	public Date getTokenExpirationDate(String token) {
		return exctractTokenClaim(token, Claims::getExpiration);
	}


	/**
	 * Extracts specified info from the JWT.
	 * 
	 * @param <T>            Type to be extracted.
	 * @param token          The JWT.
	 * @param claimsResolver The functionality
	 * @return the extracted data.
	 */
	public <T> T exctractTokenClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = exctractAllTokenClaims(token);
		return claimsResolver.apply(claims);
	}

	/**
	 * Creates a JWT.
	 * 
	 * @param userDetails The information to create the JWT from.
	 * @return a generated JWT.
	 */
	public String createToken(Client client) {
		System.out.println(secret);
		Map<String, Object> claims = new HashMap<>();
		claims.put("family", client.getFamily().getId());
		return generateToken(claims, client.getFirebaseId() );
	}


	private String generateToken(Map<String, Object> claims, String clientId) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(clientId)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS256,  secret.getBytes())
				.compact();
	}

	private boolean isTokenExpired(String token) {
		return getTokenExpirationDate(token).before(new Date());
	}

	private Claims exctractAllTokenClaims(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
}
