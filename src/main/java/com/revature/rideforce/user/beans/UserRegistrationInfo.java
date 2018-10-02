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
	public String toString() {
		return "UserRegistrationInfo [user=" + user + ", password=" + password + "]";
	}
}
