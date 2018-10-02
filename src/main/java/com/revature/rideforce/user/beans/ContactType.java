package com.revature.rideforce.user.beans;

import java.net.URI;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.web.util.UriComponentsBuilder;

import com.revature.rideforce.user.json.EnumLike;
import com.revature.rideforce.user.json.Linkable;

@Entity
@Table(name = "CONTACT_TYPE")
public class ContactType implements EnumLike, Identifiable, Linkable {
	@Id
	@Column(name = "CONTACT_TYPE_ID")
	@SequenceGenerator(name = "contacttypeid", sequenceName = "contacttypeid")
	@GeneratedValue(generator = "contacttypeid", strategy = GenerationType.SEQUENCE)
	private int id;

	@Column(nullable = false, length = 20)
	@NotEmpty
	private String type;
	
	public ContactType() {
		super();
	}
	
	public ContactType(int id, @NotEmpty String type) {
		super();
		this.id = id;
		this.type = type;
	}
	
	@Override
	public int getId() {
		return id;
	}


	@Override
	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type.toUpperCase();
	}

	public void setType(String type) {
		this.type = type.toUpperCase();
	}

	@Override
	public String toEnumString() {
		return type.toUpperCase();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((type == null) ? 0 : type.toUpperCase().hashCode());
		return result;
	}

	@Override
	public URI toUri() {
		return UriComponentsBuilder.fromPath("/contact-types/{id}").buildAndExpand(id).toUri();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContactType other = (ContactType) obj;
		if (id != other.id)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equalsIgnoreCase(other.type))
			return false;
		return true;
	}
}
