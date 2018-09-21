package com.revature.rideshare.user.jsonbeans;

import javax.validation.constraints.NotEmpty;

public class JsonContactInfo {
	private int id;

	@NotEmpty
	private String user;

	@NotEmpty
	private String type;

	@NotEmpty
	private String info;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
}
