package com.revature.rideforce.user.exceptions;

/**
 * The exception thrown when a user attempts to create a new role with the same
 * name as an existing one.
 */
public class DuplicateRoleException extends EntityConflictException {
	private static final long serialVersionUID = 1L;

	public DuplicateRoleException(String role) {
		super("Role " + role + " already exists.");
	}
}
