package com.revature.rideshare.user.services;

public class InvalidCredentialsException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidCredentialsException() {
		super("Incorrect email or password.");
	}
}
