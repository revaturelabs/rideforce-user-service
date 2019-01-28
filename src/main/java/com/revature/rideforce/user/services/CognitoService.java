package com.revature.rideforce.user.services;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.interfaces.RSAKeyProvider;

/**
 * This class provides Cognito token verification for the specified pool.
 * 
 * @author Mateusz Wiater
 * @since 2019-01-27
 */
public class CognitoService {
	private final JWTVerifier verifier;
	private final String COGNITO_POOL_URL = "https://cognito-idp.us-east-1.amazonaws.com/us-east-1_DcPwvLXtn";

	/**
	 * Constructor.
	 */
	public CognitoService() {
		verifier = JWT.require(Algorithm.RSA256(new KeyProvider())).withIssuer(COGNITO_POOL_URL).build();
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
		} catch (SignatureVerificationException e) {
			// TODO Log the exception
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
		public KeyProvider() {
			try {
				jwkProvider = new UrlJwkProvider(new URL(COGNITO_POOL_URL + "/.well-known/jwks.json"));
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



