package com.revature.rideshare.user.beans;

import java.net.URI;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotEmpty;

import org.springframework.web.util.UriComponentsBuilder;

import com.revature.rideshare.user.json.Linkable;

@Entity
public class Office implements Identifiable, Linkable {
	@Id
	@Column(name = "OFFICE_ID")
	@SequenceGenerator(name = "officeid", sequenceName = "officeid")
	@GeneratedValue(generator = "officeid", strategy = GenerationType.SEQUENCE)
	private int id;

	@Column(nullable = false, length = 30)
	@NotEmpty
	private String name;

	@Column(nullable = false, length = 100)
	@NotEmpty
	private String address;

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Override
	public URI toUri() {
		return UriComponentsBuilder.fromPath("/offices/{id}").buildAndExpand(id).toUri();
	}
}
