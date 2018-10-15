package com.revature.rideforce.user.beans.forms;

/**
 * Just holds the String token and String new password to be processed in put {@linkplain LoginRecoveryController}
 * @author clpeng
 * @since Iteration 2 10/20/2018
 */
public class LoginRecoveryProcessForm {
	private String token;
	private String newPassword;
	public LoginRecoveryProcessForm() {
		super();
	}
	public LoginRecoveryProcessForm(String token, String newPassword) {
		super();
		this.token = token;
		this.newPassword = newPassword;
	}
	public String getToken() {
		return token;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	@Override
	public String toString() {
		return "LoginRecoveryProcessForm [token=" + token + ", newPassword=" + newPassword + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((newPassword == null) ? 0 : newPassword.hashCode());
		result = prime * result + ((token == null) ? 0 : token.hashCode());
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
		LoginRecoveryProcessForm other = (LoginRecoveryProcessForm) obj;
		if (newPassword == null) {
			if (other.newPassword != null)
				return false;
		} 
		else if (!newPassword.equals(other.newPassword))
			return false;
		if (token == null) {
			if (other.token != null)
				return false;
		} 
		else if (!token.equals(other.token))
			return false;
		return true;
	}
	
	
}
