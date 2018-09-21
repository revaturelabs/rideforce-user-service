package com.revature.rideshare.user.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "ROLE")
public class UserRole {
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
}
