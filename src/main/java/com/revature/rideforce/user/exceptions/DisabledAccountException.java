package com.revature.rideforce.user.exceptions;

public class DisabledAccountException extends Exception {
	private static final long serialVersionUID = 1L;

	public DisabledAccountException() {
		super("Account is disabled.");
	}
}
