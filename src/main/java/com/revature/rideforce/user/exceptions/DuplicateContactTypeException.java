package com.revature.rideforce.user.exceptions;

/**
 * The exception thrown for trying to add in a contact type that already exists in the database
 * @author clpeng
 *
 */
public class DuplicateContactTypeException extends EntityConflictException {
	private static final long serialVersionUID = 1L;

	public DuplicateContactTypeException(String type) {
		super("Contact type " + type + " already exists.");
	}
}
