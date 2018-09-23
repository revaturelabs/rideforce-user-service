package com.revature.rideshare.user.exceptions;

public class InvalidCredentialsException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidCredentialsException() {
		super("Incorrect email or password.");
	}
}
