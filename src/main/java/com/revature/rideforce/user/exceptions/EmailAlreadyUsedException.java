package com.revature.rideforce.user.exceptions;

/**
 * The exception thrown when a user attempts to register with an email that has
 * already been used by another user.
 */
public class EmailAlreadyUsedException extends EntityConflictException {
	private static final long serialVersionUID = 1L;

	public EmailAlreadyUsedException(String email) {
		super(email + " is already used by another user.");
	}
}
