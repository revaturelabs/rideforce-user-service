package com.revature.rideshare.user.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Component
@Table(name="CONTACT_TYPE")
public class ContactType {
	
	@Id
	@Column(name="CONTACT_INFO_ID")
	@SequenceGenerator(name="contacttypeid", sequenceName="contacttypeid")
	@GeneratedValue(generator="contacttypeid", strategy=GenerationType.SEQUENCE)
	private int id;
	
	@Column(nullable=false)
	private String type;
	
	public ContactType() {
		super();
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
	public void setType(String type) {
		this.type = type;
	}
	
	

}
