package com.revature.rideforce.user.exceptions;

/**
 * The abstract superclass for all exceptions indicating that a conflict has
 * arisen when trying to add or update an entity.
 */
public abstract class EntityConflictException extends Exception {
	private static final long serialVersionUID = 1L;

	public EntityConflictException(String message) {
		super(message);
	}
}
