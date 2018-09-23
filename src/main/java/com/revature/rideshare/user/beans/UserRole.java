package com.revature.rideshare.user.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.revature.rideshare.user.json.EnumLike;

@Entity
@Table(name = "ROLE")
public class UserRole implements EnumLike {
	@Id
	@Column(name = "ROLE_ID")
	@SequenceGenerator(name = "roleid", sequenceName = "roleid")
	@GeneratedValue(generator = "roleid", strategy = GenerationType.SEQUENCE)
	private int id;

	@Column(nullable = false, length = 30)
	@NotEmpty
	private String type;

	public UserRole() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String role) {
		this.type = role;
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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserRole other = (UserRole) obj;
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
