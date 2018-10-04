package com.revature.rideforce.user.exceptions;


/**
 * The Exception thrown for entering invalid credentials during login
 * @author clpeng
 *
 */
public class InvalidCredentialsException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidCredentialsException() {
		super("Incorrect email or password.");
	}
}
