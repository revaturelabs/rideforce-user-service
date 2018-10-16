package com.revature.rideforce.user.beans;

import java.io.Serializable;
import java.net.URI;

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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.web.util.UriComponentsBuilder;

import com.revature.rideforce.user.json.JsonLink;
import com.revature.rideforce.user.json.Linkable;
import com.revature.rideforce.user.json.UserLinkResolver;

/**
 * class in the beans package representing a car's features, to be mapped in the database
 * <p> <strong>Member variables:</strong> (all private)
 * <br>int id, <br>{@linkplain com.revature.rideforce.user.beans.Car#owner User} owner, <br>String make, <br>String model, <br>int year
 * <p> <strong>Methods:</strong> <br>
 * {@linkplain #getId() }<br>
 * {@linkplain #getClass() }<br>
 * {@linkplain #getMake()} <br>
 * {@linkplain #getModel()}<br>
 * {@linkplain #getOwner()}<br>
 * {@linkplain #getYear()}<p>
 * {@linkplain #setId(int)}<br>
 * {@linkplain #setMake(String)}<br>
 * {@linkplain #setModel(String)}<br>
 * {@linkplain #setOwner(User)}<br>
 * {@linkplain #setYear(int)}<p>
 * {@linkplain #toLink()}<br>
 * {@linkplain #toString()}<br>
 * {@linkplain #toUri()}<p>
 * {@linkplain #equals(Object)}<br>
 * 
 * @since Iteration 1: 10/01/2018
 * @author clpeng
 */
@Entity
public class Car implements Identifiable, Linkable, Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Min(1)
	@Column(name = "CAR_ID")
	@SequenceGenerator(name = "carid", sequenceName = "carid")
	@GeneratedValue(generator = "carid", strategy = GenerationType.SEQUENCE)
	private int id;

	@ManyToOne(fetch = FetchType.EAGER)
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

	/* (non-Javadoc)
	 * @see com.revature.rideforce.user.beans.Identifiable#getId()
	 */
	public Car() {
		super();
		this.owner = new User();
	}

	//adding this constructor because the ID for it is actually generated auto...so even if u set it to 1, it'll be like 802...
	public Car(@NotNull @Valid User owner, @NotEmpty String make, @NotEmpty String model, int year) {
		super();
		this.owner = owner;
		this.make = make;
		this.model = model;
		this.year = year;
	}
	
	public Car(int id, @NotNull @Valid User owner, @NotEmpty String make, @NotEmpty String model, int year) {
		this(owner, make, model, year);
		this.id = id;
	}

	@Override
	public int getId() {
		return id;
	}

	
	/* (non-Javadoc)
	 * @see com.revature.rideforce.user.beans.Identifiable#setId(int)
	 */
	@Override
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return	make	make of the car as a string
	 */
	public String getMake() {
		return make;
	}

	/**
	 * @param	make	provide the make of the car, which is a string	
	 */
	public void setMake(String make) {
		this.make = make;
	}

	/**
	 * @return	model	get the model of the car, a string
	 */
	public String getModel() {
		return model;
	}

	/**
	 * @param	model	set the model of the car, provide a string
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * @return	integer year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year provide integer to set the year of the car
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * @return	owner, a User object that owns this car
	 */
	public User getOwner() {
		return owner;
	}

	/**
	 * @param owner	provide a User that will be the car's owner
	 */
	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	/* (non-Javadoc)
	 * @see com.revature.rideforce.user.json.Linkable#toUri()
	 */
	@Override
	public URI toUri() {
		return UriComponentsBuilder.fromPath("/cars/{id}").buildAndExpand(id).toUri();
	}
	
}
