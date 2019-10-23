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
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserCredentials other = (UserCredentials) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} 
		else if (!email.equals(other.email)) 
				return false;
		if (password == null) {
			if (other.password != null)
				return false;
		}
		else if (!password.equals(other.password))
			return false;
		return true;
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
