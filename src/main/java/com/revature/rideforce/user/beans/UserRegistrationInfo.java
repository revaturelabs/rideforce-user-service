package com.revature.rideforce.user.beans;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserRegistrationInfo {
	
	@Valid
	@NotNull
	private User user;
	
	@NotEmpty
	private String password;
	
	@NotEmpty
	private String registrationKey;
	
	public UserRegistrationInfo() {
		super();
		this.user = new User();
	}

	public UserRegistrationInfo(@NotEmpty User user, @NotEmpty String password, @NotEmpty String registrationKey) {
		super();
		this.user = user;
		this.password = password;
		this.registrationKey = registrationKey;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRegistrationKey() {
		return registrationKey;
	}

	public void setRegistrationKey(String registrationKey) {
		this.registrationKey = registrationKey;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((registrationKey == null) ? 0 : registrationKey.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		UserRegistrationInfo other = (UserRegistrationInfo) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} 
		else if (!password.equals(other.password))
			return false;
		if (registrationKey == null) {
			if (other.registrationKey != null)
				return false;
		} 
		else if (!registrationKey.equals(other.registrationKey))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} 
		else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserRegistrationInfo [user=" + user + ", password=" + password + "]";
	}
}
