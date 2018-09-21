package com.revature.rideshare.user.beans;

public class UserRegistrationInfo {
	private User user;
	private String password;

	public UserRegistrationInfo() {
		super();
	}

	public UserRegistrationInfo(User user, String password) {
		super();
		this.user = user;
		this.password = password;
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

	@Override
	public String toString() {
		return "UserRegistrationInfo [user=" + user + ", password=" + password + "]";
	}

}
