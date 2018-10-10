package com.revature.rideforce.user.security;

import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.Date;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

/**
 * The abstract base class for token providers, providing the basic
 * functionality needed to create and verify tokens.
 * @since Iteration1 10/01/2018
 */
public abstract class JwtProvider implements InitializingBean {
	private static final String ISSUER = "revature";

	@Value("SECRET")
	private String secret;
	private Algorithm algorithm;
	private JWTVerifier verifier;
	
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() {
		algorithm = Algorithm.HMAC256(secret);
		verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
	}

	/**
	 * Generates a new JSON Web Token.
	 * 
	 * @param subject   the subject of the token (e.g. a user ID for login)
	 * @param expiresIn how long the token should be valid
	 * @return the JWT, as a string
	 */
	protected String generateToken(String subject, TemporalAmount expiresIn) {
		Instant now = Instant.now();
		Instant expires = now.plus(expiresIn);
		try {
			return JWT.create().withIssuer(ISSUER).withSubject(subject).withIssuedAt(Date.from(now))
					.withExpiresAt(Date.from(expires)).sign(algorithm);
		} catch (JWTCreationException e) {
			return null;
		}
	}

	/**
	 * Given a token, gets the subject of the token after verifying that it is
	 * correctly signed.
	 * 
	 * @param token the token from which to get the subject
	 * @return the subject of the token, if it could be found and the token was
	 *         valid, or {@code null} if either condition was false
	 */
	protected String getSubject(String token) {
		try {
			return verifier.verify(token).getSubject();
		} catch (JWTVerificationException e) {
			return null;
		}
	}
}
