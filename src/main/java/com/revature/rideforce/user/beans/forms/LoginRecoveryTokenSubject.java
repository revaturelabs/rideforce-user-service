package com.revature.rideforce.user.beans.forms;

import java.util.Date;

/**
 * Object to be saved as the subject of LoginRecoveryToken
 * @author clpeng
 *
 */
public class LoginRecoveryTokenSubject {
	private int id;
	private String password;
	private Date date;
	public LoginRecoveryTokenSubject() {
		super();
	}
	public LoginRecoveryTokenSubject(int id, String password, Date date) {
		super();
		this.id = id;
		this.password = password;
		this.date = date;
	}
	public int getId() {
		return id;
	}
	public String getPassword() {
		return password;
	}
	public Date getDate() {
		return date;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "LoginRecoveryTokenSubject [id=" + id + ", password=" + password + ", date=" + date + "]";
	}
	
	
}
