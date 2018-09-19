package com.revature.rideshare.user.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Component
@Table(name="ROLE")
public class UserRole {
	
	@Id
	@Column(name="ROLE_ID")
	private int id;
	
	@Column(nullable=false)
	private String type;
	
	public UserRole() {
		super();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRole() {
		return type;
	}
	public void setRole(String role) {
		this.type = role;
	}
	
	
	

}
