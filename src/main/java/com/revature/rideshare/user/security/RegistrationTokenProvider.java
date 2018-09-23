package com.revature.rideshare.user.security;

import java.time.Duration;

import org.springframework.stereotype.Service;

/**
 * A provider for registration tokens, which can be used to create a new user.
 */
@Service
public class RegistrationTokenProvider extends JwtProvider {
	/**
	 * The subject associated with every registration token.
	 */
	public static final String SUBJECT = "REGISTRATION";

	/**
	 * Generates a new registration token.
	 * 
	 * @return a registration token, which expires in two hours after creation
	 */
	public String generateToken() {
		return super.generateToken(SUBJECT, Duration.ofHours(2));
	}

	/**
	 * Determines whether the given token is valid.
	 * 
	 * @param token the registration token to check
	 * @return whether the given token was valid
	 */
	public boolean isValid(String token) {
		String subject = getSubject(token);
		return subject == null ? false : subject.equals(SUBJECT);
	}
}
