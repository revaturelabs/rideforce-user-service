package com.revature.rideforce.user.exceptions;

/**
 * The exception thrown when a user attempts to access or modify a resource
 * without proper permissions.
 */
public class PermissionDeniedException extends Exception {
	private static final long serialVersionUID = 1L;

	public PermissionDeniedException(String message) {
		super(message);
	}
}
