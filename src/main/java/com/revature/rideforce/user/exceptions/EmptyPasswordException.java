package com.revature.rideforce.user.exceptions;

public class EmptyPasswordException extends Exception{
	private static final long serialVersionUID = 1L;

	public EmptyPasswordException() {
		super("Can not set user's password to an empty password");
	}
}
