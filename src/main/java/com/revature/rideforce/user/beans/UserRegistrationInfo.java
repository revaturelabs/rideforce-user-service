package com.revature.rideforce.user.beans;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserRegistrationInfo {
	
	@Valid
	@NotNull
	private User user;
	
	@NotEmpty
	private String idToken;
	
	@NotEmpty
	private String registrationToken;
	
	public UserRegistrationInfo() {
		super();
		this.user = new User();
	}

	public UserRegistrationInfo(@NotEmpty User user, @NotEmpty String idToken, @NotEmpty String registrationToken) {
		super();
		this.user = user;
		this.idToken = idToken;
		this.registrationToken = registrationToken;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getIdToken() {
		return idToken;
	}

	public void setIdToken(String idToken) {
		this.idToken = idToken;
	}

	public String getRegistrationToken() {
		return registrationToken;
	}

	public void setRegistrationToken(String registrationToken) {
		this.registrationToken = registrationToken;
	}

	@Override
	public String toString() {
		return "UserRegistrationInfo [user=" + user + ", idToken=" + idToken + ", registrationToken="
				+ registrationToken + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idToken == null) ? 0 : idToken.hashCode());
		result = prime * result + ((registrationToken == null) ? 0 : registrationToken.hashCode());
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
		if (idToken == null) {
			if (other.idToken != null)
				return false;
		} else if (!idToken.equals(other.idToken))
			return false;
		if (registrationToken == null) {
			if (other.registrationToken != null)
				return false;
		} else if (!registrationToken.equals(other.registrationToken))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
}
