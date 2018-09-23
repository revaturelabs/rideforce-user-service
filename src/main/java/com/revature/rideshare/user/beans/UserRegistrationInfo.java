package com.revature.rideshare.user.beans;

public class UserRegistrationInfo {
	private User user;
	private String password;
	private String registrationKey;

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
