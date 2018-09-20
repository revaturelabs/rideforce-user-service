package com.revature.rideshare.user.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

@Entity
@Component
public class Address {
	
	@Id
	@Column(name="ADDRESS_ID")
	@SequenceGenerator(name="addressid", sequenceName="addressid")
	@GeneratedValue(generator="addressid", strategy=GenerationType.SEQUENCE)
	private int id;
	
	@Column(nullable=false, length=80)
	private String address;
	
	@Column(length=80)
	private String address2;
	
	@Column(nullable=false, length=30)
	private String city;
	
	@Column(nullable=false, length=25)
	private String state;
	
	@Column(nullable=false)
	private int zip;
	
	public Address() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLine1() {
		return address;
	}
	public void setLine1(String line1) {
		this.address = line1;
	}
	public String getLine2() {
		return address2;
	}
	public void setLine2(String line2) {
		this.address2 = line2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getZip() {
		return zip;
	}
	public void setZip(int zip) {
		this.zip = zip;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + id;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((address2 == null) ? 0 : address2.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + zip;
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
		Address other = (Address) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (id != other.id)
			return false;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (address2 == null) {
			if (other.address2 != null)
				return false;
		} else if (!address2.equals(other.address2))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (zip != other.zip)
			return false;
		return true;
	}

}
