package com.revature.rideshare.user.beans;

import org.springframework.util.AntPathMatcher;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Car {

	private int id;
	private User owner;
	private String make;
	private String model;
	private int year;

	public Car() {
		super();
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

	/**
	 * Gets the owner from a link specified in JSON.
	 * 
	 * @param uri the URI linking to the owner
	 */
	@JsonProperty("owner")
	public void getOwnerJson(String uri) {
		AntPathMatcher matcher = new AntPathMatcher();
		// TODO: add some input validation or something.
		int userId = Integer.parseInt(matcher.extractUriTemplateVariables("/users/{id}", uri).get("id"));
		// TODO: retrieve the user object here.
		owner = null;
	}
	
	/**
	 * Sets the owner link property in JSON.
	 * 
	 * @return a link to the owner
	 */
	@JsonProperty("owner")
	public String setOwnerJson() {
		return UriComponentsBuilder.fromPath("/users/{id}").buildAndExpand(owner.getId()).toUriString();
	}
}
