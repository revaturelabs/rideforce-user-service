package com.revature.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Represents a Role's features, to be mapped in the database.
 * 
 * @author Damion Monroe, Krishna Kafley, Joshua Ellison
 * 
 * @version 1908sdet.0.1
 *
 */
@Entity
@Table(name = "roles")
public class Role {

	/**
	 * This variable is the unique identifier / primary key for the roles table in
	 * the database.
	 */
	@Id
	@SequenceGenerator(name = "role_id_maker", sequenceName = "role_id_maker", allocationSize = 1)
	@GeneratedValue(generator = "role_id_maker", strategy = GenerationType.SEQUENCE)
	@Column(name = "RID")
	private Integer id;

	/**
	 * This variable is the name of the role, driver or rider, associated with the
	 * RID.
	 */
	@Column(name = "rname", unique = true)
	private String rname;

	/**
	 * Basic constructor for the Role Model.
	 */
	public Role() {
		super();
	}

	/**
	 * Constructor for the Role Model without id.
	 * 
	 * @param rname
	 */
	public Role(String rname) {
		super();
		this.rname = rname;
	}

	/**
	 * Full constructor for the role table.
	 * 
	 * @param id
	 * @param rname
	 */
	public Role(Integer id, String rname) {
		super();
		this.id = id;
		this.rname = rname;
	}
	// begin getters and setters

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRname() {
		return rname;
	}

	public void setRname(String rname) {
		this.rname = rname;
	}
	// end getters and setters

	/**
	 * toString method for easy debugging and console output.
	 */
	@Override
	public String toString() {
		return "Role [id=" + id + ", rname=" + rname + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((rname == null) ? 0 : rname.hashCode());
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
		Role other = (Role) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		if (rname == null) {
			if (other.rname != null)
				return false;
		}
		else if (!rname.equals(other.rname))
			return false;
		return true;
	}

}
