package com.revature.rideforce.user.beans;

import javax.validation.constraints.NotEmpty;

public class UserCredentials {
	@NotEmpty
	private String email;

	@NotEmpty
	private String password;

	public UserCredentials() {
	}

	public UserCredentials(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
/**
  Gets information about this object's state
  @return string containing ["email=" <code>email</code>, "password"= <code>password</code>]
  */
	@Override
	public String toString() {
		return "UserCredentials [email=" + email + ", password=" + password + "]";
	}
}
