package com.revature.rideforce.user.beans;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The object sent with a request to change a user's password (consists of the
 * old and new passwords).
 */
public class PasswordChangeRequest {
	@JsonProperty("old")
	@NotEmpty
	private String oldPassword;

	@JsonProperty("new")
	@NotEmpty
	private String newPassword;
	
	

	public PasswordChangeRequest() {
		super();
	}
	
	public PasswordChangeRequest(@NotEmpty String oldPassword, @NotEmpty String newPassword) {
		super();
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}



	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((newPassword == null) ? 0 : newPassword.hashCode());
		result = prime * result + ((oldPassword == null) ? 0 : oldPassword.hashCode());
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
		PasswordChangeRequest other = (PasswordChangeRequest) obj;
		if (newPassword == null) {
			if (other.newPassword != null)
				return false;
		} else if (!newPassword.equals(other.newPassword)) {
			return false;
		}
		if (oldPassword == null) {
			if (other.oldPassword != null)
				return false;
		} else if (!oldPassword.equals(other.oldPassword)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "PasswordChangeRequest [oldPassword=" + oldPassword + ", newPassword=" + newPassword + "]";
	}
	
	
}
