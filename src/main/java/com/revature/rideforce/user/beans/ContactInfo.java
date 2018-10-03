package com.revature.rideforce.user.beans;

import java.net.URI;
import java.util.ArrayList;

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
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.web.util.UriComponentsBuilder;

import com.revature.rideforce.user.json.ContactTypeResolver;
import com.revature.rideforce.user.json.JsonEnumLike;
import com.revature.rideforce.user.json.Linkable;

import antlr.collections.List;



/**
 * bean mapped to the database. Models a user's contact information
 * <p> <strong>Member Variables:</strong> <code><br>int id, <br>User user,<br> {@linkplain com.revature.rideforce.user.beans.ContactType ContactType} type</code>,<br> <code>String info</code>
 *@since Iteration 1: 10/01/2018
 */
@Entity
@Table(name = "CONTACT_INFO")
public class ContactInfo implements Identifiable, Linkable {
	
	@Id
	@Min(1)
	@Column(name = "CONTACT_INFO_ID")
	@SequenceGenerator(name = "contactinfoid", sequenceName = "contactinfoid")
	@GeneratedValue(generator = "contactinfoid", strategy = GenerationType.SEQUENCE)
	private int id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_ID", nullable = false)
	@NotNull
	@Valid
	private User user;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "CONTACT_TYPE_ID", nullable = false)
	@NotNull
	@Valid
	@JsonEnumLike(ContactTypeResolver.class)
	private ContactType type;

	@Column(length = 100)
	@NotEmpty
	private String info;
	
	

	public ContactInfo() {
		super();
	}

	public ContactInfo(@Min(1) int id, @NotNull @Valid User user, @NotNull @Valid ContactType type,
			@NotEmpty String info) {
		super();
		this.id = id;
		this.user = user;
		this.type = type;
		this.info = info;
	}

	/* (non-Javadoc)
	 * @see com.revature.rideforce.user.beans.Identifiable#getId()
	 */
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

	/** gets the User member variable of this object
	 * @return <code>User</code> of this object
	 * @see com.revature.rideforce.user.beans.User User
	 */
	public User getUser() {
		return user;
	}

	/** sets the User member variable of this object
	 * @param user the new User object to be saved in the user member variable
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/** get the contact type member variable
	 * @return type of the object, which is a ContactType object
	 * @see com.revature.rideforce.user.beans.ContactType ContactType
	 */
	public ContactType getType() {
		return type;
	}

	/**Set the contact type member variable
	 * @param type the new ContactType object to be saved in ContactInfo's member variable type
	 * @see com.revature.rideforce.user.beans.ContactType ContactType
	 */
	public void setType(ContactType type) {
		this.type = type;
	}

	/**Get the ContactType.info member variable
	 * @return <code>String</code> the information for this contact
	 */
	public String getInfo() {
		return info;
	}

	/**Set the ContactType.info member variable
	 * @param value a String that specifies the new info to be set for the contact
	 */
	public void setInfo(String value) {
		this.info = value;
	}
	
	/* (non-Javadoc)
	 * @see com.revature.rideforce.user.json.Linkable#toUri()
	 */
	@Override
	public URI toUri() {
		return UriComponentsBuilder.fromPath("/contact-info/{id}").buildAndExpand(id).toUri();
	}
}
