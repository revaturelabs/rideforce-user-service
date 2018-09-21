package com.revature.rideshare.user.jsonbeans;

public class JsonUserRegistrationInfo {
	private JsonUser user;
	private String password;

	public JsonUser getUser() {
		return user;
	}

	public void setUser(JsonUser user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
