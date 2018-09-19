package com.revature.rideshare.user.beans;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.springframework.stereotype.Component;

@Entity
@Component
public class Office {
	
	@Id
	@Column(name="OFFICE_ID")
	@SequenceGenerator(name="officeid", sequenceName="officeid")
	@GeneratedValue(generator="officeid", strategy=GenerationType.SEQUENCE)
	private int id;
	
	@Column(nullable=false)
	private String name;
	
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="ADDRESS_ID")
	@Column(name="ADDRESS_ID", nullable=false)
	private Address address;
	
	public Office() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	

}
