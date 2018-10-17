package com.revature.rideforce.user.exceptions;

/**
 * An exception thrown when a user attempts to register with an invalid key.
 */
public class InvalidRegistrationKeyException extends CustomException {
	private static final long serialVersionUID = 1L;

	public InvalidRegistrationKeyException() {
		super("Invalid registration key.");
	}
}
