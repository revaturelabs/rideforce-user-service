package com.revature.rideforce.user.beans.changeModels;

import java.util.Date;

import com.revature.rideforce.user.beans.Office;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.beans.UserRole;

public class ChangeUserModel {

	private int id;
	private String firstName;
	private String lastName;
	private String password;
	private String email;
	private String photoUrl;
	private String address;
	private Office office;
	private Date batchEnd;
	private UserRole role;
	private Boolean active;
	
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
	
	public UserRole getRole() {
		return role;
	}
	
	public void setRole(UserRole role) {
		this.role = role;
	}
	
	public Boolean getActive() {
		return active;
	}
	
	public void setActive(Boolean active) {
		this.active = active;
	}

    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((batchEnd == null) ? 0 : batchEnd.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + id;
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((office == null) ? 0 : office.hashCode());
		result = prime * result + ((photoUrl == null) ? 0 : photoUrl.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
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
		ChangeUserModel other = (ChangeUserModel) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (batchEnd == null) {
			if (other.batchEnd != null)
				return false;
		} else if (!batchEnd.equals(other.batchEnd))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id != other.id)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (office == null) {
			if (other.office != null)
				return false;
		} else if (!office.equals(other.office))
			return false;
		if (photoUrl == null) {
			if (other.photoUrl != null)
				return false;
		} else if (!photoUrl.equals(other.photoUrl))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		return true;
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
		if(role != null)
			original.setRole(role);
		if(active != null)
			original.setActive(active);
		if(password != null)
			original.setPassword(password);
	}
	
	
}
