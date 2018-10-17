package com.revature.rideforce.user.exceptions;

/**
 * The exception thrown for registering with password that doesn't meet requirements 
 * @author Newton Hoac
 *
 */
public class PasswordRequirementsException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public PasswordRequirementsException() {
		super("Password does not meet requirements");
	}
}
