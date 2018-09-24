package com.revature.rideshare.user.exceptions;

public class DuplicateContactTypeException extends EntityConflictException {
	private static final long serialVersionUID = 1L;

	public DuplicateContactTypeException(String type) {
		super("Contact type " + type + " already exists.");
	}
}
