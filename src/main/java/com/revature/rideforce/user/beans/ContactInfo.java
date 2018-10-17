package com.revature.rideforce.user.beans;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.web.util.UriComponentsBuilder;

import com.revature.rideforce.user.json.ContactTypeResolver;
import com.revature.rideforce.user.json.JsonEnumLike;
import com.revature.rideforce.user.json.Linkable;

@Entity
@Table(name = "CONTACT_INFO")
public class ContactInfo implements Identifiable, Linkable, Serializable {
	private static final long serialVersionUID = 1L;

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

	@Override
	public int getId() {
		return id;
	}

	@Override
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
	
	@Override
	public URI toUri() {
		return UriComponentsBuilder.fromPath("/contact-info/{id}").buildAndExpand(id).toUri();
	}
}