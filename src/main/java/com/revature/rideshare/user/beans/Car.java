package com.revature.rideshare.user.beans;

import java.net.URI;

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

import org.springframework.web.util.UriComponentsBuilder;

import com.revature.rideshare.user.json.JsonLink;
import com.revature.rideshare.user.json.Linkable;
import com.revature.rideshare.user.json.UserLinkResolver;

@Entity
public class Car implements Identifiable, Linkable {
	@Id
	@Column(name = "CAR_ID")
	@SequenceGenerator(name = "carid", sequenceName = "carid")
	@GeneratedValue(generator = "carid", strategy = GenerationType.SEQUENCE)
	private int id;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_ID", nullable = false)
	@NotNull
	@Valid
	@JsonLink(UserLinkResolver.class)
	private User owner;

	@Column(length = 35)
	@NotEmpty
	private String make;

	@Column(length = 30)
	@NotEmpty
	private String model;

	@Column(nullable = true)
	private int year;

	@Override
	public int getId() {
		return id;
	}

	@Override
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

	@Override
	public URI toUri() {
		return UriComponentsBuilder.fromPath("/cars/{id}").buildAndExpand(id).toUri();
	}
}
