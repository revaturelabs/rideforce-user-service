package com.revature.rideforce.user.services;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.interfaces.RSAKeyProvider;
import com.revature.rideforce.user.repository.UserRepository;

/**
 * This class provides Cognito token verification for the specified pool.
 * 
 * @author Mateusz Wiater
 * @since 2019-01-27
 */
@Service
public class CognitoService {
	@Autowired
	private Logger log;
	
	@Autowired
	private UserRepository ur;
	
	private JWTVerifier verifier;
	
	/**
	 * Constructor.
	 * 
	 * @param cognitoPoolUrl the Cognito pool URL for this service.
	 */
	public CognitoService(@Value("${COGNITO_POOL_URL}") String cognitoPoolUrl) {
		verifier = JWT.require(Algorithm.RSA256(new KeyProvider(cognitoPoolUrl)))
				.withIssuer(cognitoPoolUrl)
				.build();
	}

	/**
	 * Converts the given JWT into an authentication object that can be stored as the
	 * authentication principal in Spring's SecurityContext.
	 * 
	 * @param token the JWT to convert.
	 * @return the authentication object, or null if the given JWT was invalid.
	 */
	public Authentication authenticate(String token) {
		return Optional.ofNullable(token)
				// Verify the token
				.map(this::verify)
				// Extract the email from the token
				.map(t -> t.getClaim("email"))
				// Convert the email claim to a string
				.map(Claim::asString)
				// Find a user by the email
				.map(ur::findByEmail)
				// Create an authentication object from the user
				.map(u -> new UsernamePasswordAuthenticationToken(u, "", u.getAuthorities()))
				// Return null if anything in this chain is null
				.orElse(null);
	}

	/**
	 * Verifies the passed token against the specified pool.
	 * 
	 * @param token the token to be verified.
	 * @return a decoded token or null if the token did not pass verification.
	 */
	public DecodedJWT verify(String token) {
		try {
			return verifier.verify(token);
		} catch (Exception e) {
			log.warn(e.getMessage());
			return null;
		}
	}

	/**
	 * Key provider for Cognito.
	 * 
	 * @author Mateusz Wiater
	 * @since 2019-01-27
	 */
	private class KeyProvider implements RSAKeyProvider {
		private JwkProvider jwkProvider;

		/**
		 * Constructor.
		 */
		public KeyProvider(String cognitoPoolUrl) {
			try {
				jwkProvider = new UrlJwkProvider(new URL(cognitoPoolUrl + "/.well-known/jwks.json"));
			} catch (MalformedURLException e) {
				// TODO Log the exception
			}
		}

		@Override
		public RSAPublicKey getPublicKeyById(String keyId) {
			try {
				return (RSAPublicKey) jwkProvider.get(keyId).getPublicKey();
			} catch (JwkException e) {
				// TODO Log the exception
				return null;
			}
		}

		@Override
		public RSAPrivateKey getPrivateKey() {
			return null;
		}

		@Override
		public String getPrivateKeyId() {
			return null;
		}
	}
}



