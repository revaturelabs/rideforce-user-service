package com.revature.rideforce.user.beans.forms;

import java.util.Date;

import com.revature.rideforce.user.beans.Office;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.exceptions.EmptyPasswordException;

public class ChangeUserModel {

	private int id;
	private String firstName;
	private String lastName;
	private String password;
	private String email;
	private String photoUrl;
	private String address;
	private Office office;
	private float startTime;
	private Date batchEnd;
	private String role;
	private String active;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPhotoUrl() {
		return photoUrl;
	}
	
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public Office getOffice() {
		return office;
	}
	
	public void setOffice(Office office) {
		this.office = office;
	}
	
	public Date getBatchEnd() {
		return batchEnd;
	}
	
	public void setBatchEnd(Date batchEnd) {
		this.batchEnd = batchEnd;
	}

	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getActive() {
		return active;
	}
	
	public void setActive(String active) {
		this.active = active;
	}

    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public float getStartTime() {
		return startTime;
	}
	
	public void setStartTime(float startTime) {
		this.startTime = startTime;
	}

	@Override
	public String toString() {
		return "ChangeUserModel [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", photoUrl=" + photoUrl + ", address=" + address + ", office=" + office + ", batchEnd=" + batchEnd
				+ ", role=" + role + "]";
	}
	
	public void changeUser(User original)
	{
		if(firstName != null)
			original.setFirstName(firstName);
		if(lastName != null)
			original.setLastName(lastName);
		if(email != null)
			original.setEmail(email.toLowerCase());
		if(photoUrl != null)
			original.setPhotoUrl(photoUrl);
		if(address != null)
			original.setAddress(address);
		if(office != null)
			original.setOffice(office);
		if(batchEnd != null)
			original.setBatchEnd(batchEnd);
		if(active != null)
			original.setActive(active);
		if(password != null)
			try {
				original.setPassword(password);
			} catch (EmptyPasswordException e) {
				//don't change the password if it's empty ""
			}
		if(startTime < 0.0)
			original.setStartTime(startTime);
	}
	
	
}
