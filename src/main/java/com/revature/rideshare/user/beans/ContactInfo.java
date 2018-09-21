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
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.revature.rideshare.user.services.ContactTypeService;

@Entity
@Table(name = "CONTACT_INFO")
public class ContactInfo {
	@Autowired
	private transient ContactTypeService contactTypeService;

	@Id
	@Column(name = "CONTACT_INFO_ID")
	@SequenceGenerator(name = "contactinfoid", sequenceName = "contactinfoid")
	@GeneratedValue(generator = "contactinfoid", strategy = GenerationType.SEQUENCE)
	private int id;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "CONTACT_TYPE_ID", nullable = false)
	private ContactType type;

	@Column(length = 100)
	private String info;

	public ContactInfo() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ContactType getType() {
		return type;
	}

	public void setType(ContactType type) {
		this.type = type;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String value) {
		this.info = value;
	}

	/**
	 * Gets the type of this contact information as a string.
	 * 
	 * @return this contact info's type as an uppercase string
	 */
	@JsonProperty("type")
	public String getTypeString() {
		return type.getType().toUpperCase();
	}

	/**
	 * Sets the type of this contact information as a string.
	 * 
	 * @param type the new contact info type, as a string (case does not matter)
	 * @throws IllegalArgumentException if the given type string does not correspond
	 *                                  to a contact type
	 */
	@JsonProperty("type")
	public void setTypeString(String type) {
		ContactType newType = contactTypeService.findByType(type);
		if (newType == null) {
			throw new IllegalArgumentException(type + " is not a valid contact type.");
		}
		this.type = newType;
	}
}
