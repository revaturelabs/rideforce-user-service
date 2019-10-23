package com.revature.rideforce.user.beans;

import java.io.Serializable;
import java.net.URI;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.web.util.UriComponentsBuilder;

import com.revature.rideforce.user.json.Linkable;

/**models an office of Revature
 * <p><strong>Member Variables: </strong><br>
 * int id<br>
 * String name<br>
 * String address<br>
 * @author clpeng
 * @since Iteration 1: 10/01/2018
 *
 */
@Entity
public class Office implements Identifiable, Linkable, Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Min(1)
	@Column(name = "OFFICE_ID")
	@SequenceGenerator(name = "officeid", sequenceName = "officeid")
	@GeneratedValue(generator = "officeid", strategy = GenerationType.SEQUENCE)
	private int id;

	@Column(nullable = false, length = 30)
	@NotEmpty
	private String name;

	@Column(nullable = false, length = 100)
	@NotEmpty
	private String address;

	public Office() {
		super();
	}
	
	public Office(int id, @NotEmpty String name, @NotEmpty String address) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
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

	/**Get the name of the office as a String
	 * @return <code>String</code> name of the office
	 */
	public String getName() {
		return name;
	}

	/**Set the String name of the office
	 * @param name String object that will be the new value of<code> name</code> member variable
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**get the address of the office as a <code>String</code>
	 * @return <code>String</code> address of the office
	 */
	public String getAddress() {
		return address;
	}

	/**set the address of the office as a <code>String</code>
	 * @param address String object that will be the new value of <code>address</code> member variable
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/* (non-Javadoc)
	 * @see com.revature.rideforce.user.json.Linkable#toUri()
	 */
	@Override
	public URI toUri() {
		return UriComponentsBuilder.fromPath("/offices/{id}").buildAndExpand(id).toUri();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Office other = (Office) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address)) {
			return false;
		}
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Office [id=" + id + ", name=" + name + ", address=" + address + "]";
	}
	
	
}
