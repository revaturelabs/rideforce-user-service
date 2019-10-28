package com.revature.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Represents a Location's features, to be mapped in the database
 * 
 * @author Michael Rollberg
 *
 */
@Entity
@Table(name = "Locations")
public class Location {

	/**
	 * The primary key to identify a Location by
	 */
	@Id
	@SequenceGenerator(name = "location_id_maker", sequenceName = "location_id_maker", allocationSize = 1)
	@GeneratedValue(generator = "location_id_maker", strategy = GenerationType.SEQUENCE)
	@Column(name = "LID")
	private Integer lid;

	/**
	 * The street address of this Location
	 * Example: 1600 Amphitheatre Parkway
	 */
	@Column(name = "ADDRESS", nullable = false)
	private String address;

	/**
	 * The city of this Location
	 * Example: Mountain View
	 */
	@Column(name = "CITY", nullable = false)
	private String city;

	/**
	 * The two letter state code of this Location
	 * Example: CA
	 */
	@Column(name = "STATE", nullable = false, length = 2)
	private String state;

	/**
	 * The zipcode of this Location
	 * Example: 94043
	 */
	@Column(name = "ZIP", nullable = false)
	private String zip;

	/**
	 * The latitude of this Location
	 */
	@Column(name = "LATITUDE", nullable = false)
	private Double latitude;

	/**
	 * The longitude of this Location
	 */
	@Column(name = "LONGITUDE", nullable = false)
	private Double longitude;

	/**
	 * Generic constructor for Location
	 */
	public Location() {
		super();
	}

	/**
	 * Constructor based on parameters listed below
	 * Useful when creating transient Locations since they do not have an lid yet
	 * and not have a latitude or longitude
	 * 
	 * @param address - The street address of this Location
	 * @param city    - The city of this Location
	 * @param state   - The two letter state code of this Location
	 * @param zip     - The zipcode of this Location
	 */
	public Location(String address, String city, String state, String zip) {
		super();
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}

	/**
	 * Constructor based on parameters below
	 * 
	 * @param lid     - The primary key to identify a Location by
	 * @param address - The street address of this Location
	 * @param city    - The city of this Location
	 * @param state   - The two letter state code of this Location
	 * @param zip     - The zipcode of this Location
	 */
	public Location(Integer lid, String address, String city, String state, String zip) {
		super();
		this.lid = lid;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}

	/**
	 * Constructor based on parameters listed below
	 * Useful when creating transient Locations since they do not have an lid yet
	 * 
	 * @param address   - The street address of this Location
	 * @param city      - The city of this Location
	 * @param state     - The two letter state code of this Location
	 * @param zip       - The zipcode of this Location
	 * @param latitude  - The latitude of this Location
	 * @param longitude - The longitude of this Location
	 */
	public Location(String address, String city, String state, String zip, Double latitude, Double longitude) {
		super();
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * Constructor based on parameters list below
	 * Useful for persistent or detached Locations since they have an lid
	 * 
	 * @param lid       - The primary key to identify a Location by
	 * @param address   - The street address of this Location
	 * @param city      - The city of this Location
	 * @param state     - The two letter state code of this Location
	 * @param zip       - The zipcode of this Location
	 * @param latitude  - The latitude of this Location
	 * @param longitude - The longitude of this Location
	 */
	public Location(Integer lid, String address, String city, String state, String zip, Double latitude,
			Double longitude) {
		super();
		this.lid = lid;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * Get lid of Location
	 * 
	 * @return Integer - lid of Location
	 */
	public Integer getLid() {
		return lid;
	}

	/**
	 * Change the lid of Location
	 * 
	 * @param lid - new lid
	 */
	public void setLid(Integer lid) {
		this.lid = lid;
	}

	/**
	 * Get address of Location
	 * 
	 * @return String - address of Location
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Change the address of Location
	 * 
	 * @param address - new address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Get city of Location
	 * 
	 * @return String - city of Location
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Change the city of Location
	 * 
	 * @param city - new city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Get state of Location
	 * 
	 * @return String - state of Location
	 */
	public String getState() {
		return state;
	}

	/**
	 * Change the state of Location
	 * 
	 * @param state - new state
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Get zipcode of Location
	 * 
	 * @return zipcode - zipcode of Location
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * Change the zipcode of Location
	 * 
	 * @param zipcode - new zipcode
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * Get latitude of Location
	 * 
	 * @return Double - latitude of Location
	 */
	public Double getLatitude() {
		return latitude;
	}

	/**
	 * Change the latitude of Location
	 * 
	 * @param latitude - new latitude
	 */
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	/**
	 * Get longitude of Location
	 * 
	 * @return Double - id of Location
	 */
	public Double getLongitude() {
		return longitude;
	}

	/**
	 * Change the longitude of Location
	 * 
	 * @param longitude - new longitude
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	/**
	 * Generate hash code based on lid, address, city, state, zip, latitude, and
	 * longitude
	 * 
	 * @return int - hash code
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((lid == null) ? 0 : lid.hashCode());
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((zip == null) ? 0 : zip.hashCode());
		return result;
	}

	/**
	 * Check equality based on lid, address, city, state, zip, latitude, and
	 * longitude
	 * 
	 * @param obj - obj to check equality against
	 * @return boolean - is object equal to this Location
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		}
		else if (!address.equals(other.address))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		}
		else if (!city.equals(other.city))
			return false;
		if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude))
			return false;
		if (lid == null) {
			if (other.lid != null)
				return false;
		}
		else if (!lid.equals(other.lid))
			return false;
		if (Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		}
		else if (!state.equals(other.state))
			return false;
		if (zip == null) {
			if (other.zip != null)
				return false;
		}
		else if (!zip.equals(other.zip))
			return false;
		return true;
	}

	/**
	 * Generate string based on lid, address, city, state, zip, latitude, and
	 * longitude
	 * 
	 * @return String - Location in string form
	 */
	@Override
	public String toString() {
		return "Location [lid=" + lid + ", address=" + address + ", city=" + city + ", state=" + state + ", zip=" +
				zip + ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}

}
