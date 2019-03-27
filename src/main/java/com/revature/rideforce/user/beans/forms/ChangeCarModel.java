package com.revature.rideforce.user.beans.forms;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.revature.rideforce.user.beans.Car;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.json.JsonLink;
import com.revature.rideforce.user.json.UserLinkResolver;

/*
 * useless file dont use this
 * 
 * */
public class ChangeCarModel {
	private int id;
	private User owner;
	private String make;
	private String model;
	private int year;
	private String license;
	private String color;
	
	public void changeCar(Car original)
	{
		if(owner != null)
			original.setOwner(owner);
		if(make != null)
			original.setMake(make);
		if(model != null)
			original.setModel(model);
		if(year != 0)
			original.setYear(year);
		if(license != null)
			original.setLicense(license);
		if(color != null)
			original.setColor(color);
	}

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	@Override
	public String toString() {
		return "ChangeCarModel [id=" + id + ", owner=" + owner + ", make=" + make + ", model=" + model + ", year="
				+ year + ", license=" + license + ", color=" + color + "]";
	}

	
}
