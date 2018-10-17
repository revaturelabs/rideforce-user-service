package com.revature.rideforce.user.exceptions;

public class DisabledUserException extends Exception {
	private static final long serialVersionUID = 1L;

	public DisabledUserException() {
		super("Account is disabled.");
	}
}
