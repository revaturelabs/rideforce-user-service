package com.revature.rideforce.user.exceptions;

public class DisabledAccountException extends CustomException {
	private static final long serialVersionUID = 1L;

	public DisabledAccountException() {
		super("Account is disabled.");
    
	}
}
