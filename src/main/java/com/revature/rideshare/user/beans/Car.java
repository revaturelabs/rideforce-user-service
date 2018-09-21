package com.revature.rideshare.user.beans;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.revature.rideshare.user.services.UserService;

@Entity
public class Car {
	@Autowired
	private transient UserService userService;

	@Id
	@Column(name = "CAR_ID")
	@SequenceGenerator(name = "carid", sequenceName = "carid")
	@GeneratedValue(generator = "carid", strategy = GenerationType.SEQUENCE)
	private int id;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_ID", nullable = false)
	@NotNull
	@Valid
	private User owner;

	@Column(length = 35)
	@NotEmpty
	private String make;

	@Column(length = 30)
	@NotEmpty
	private String model;

	@Column(nullable = true)
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

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	/**
	 * Gets the owner property as a link.
	 * 
	 * @return a link to the owner
	 */
	@JsonProperty("owner")
	public String getOwnerLink() {
		return UriComponentsBuilder.fromPath("/users/{id}").buildAndExpand(owner.getId()).toString();
	}

	/**
	 * Sets the owner from a link string.
	 * 
	 * @param uri the URI linking to the owner
	 */
	@JsonProperty("owner")
	public void setOwnerLink(String uri) {
		AntPathMatcher matcher = new AntPathMatcher();
		int userId = Integer.parseInt(matcher.extractUriTemplateVariables("/users/{id}", uri).get("id"));
		owner = userService.findById(userId);
	}
}
