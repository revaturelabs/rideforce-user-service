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

/** Class that describes the type of the contact
 * <br> Used as a member variable of the class ContactInfo.<p>
 * <strong>Member Variables:</strong><br>
 * int id <br>
 * String type
 * @see com.revature.rideforce.user.beans.ContactInfo ContactInfo
 * @author clpeng
 * @since Iteration 1: 10/01/2018
 *
 */
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

	/**gets the type of the object, which will always be capitalized
	 * @return <code>String</code> type of the object
	 */
	public String getType() {
		return type.toUpperCase();
	}

	/**sets the type of the object, which will always be capitalized
	 * @param type the String object that will be ContactType.type's new value
	 */
	public void setType(String type) {
		this.type = type.toUpperCase();
	}

	/* (non-Javadoc)
	 * @see com.revature.rideforce.user.json.EnumLike#toEnumString()
	 */
	@Override
	public String toEnumString() {
		return type.toUpperCase();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((type == null) ? 0 : type.toUpperCase().hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see com.revature.rideforce.user.json.Linkable#toUri()
	 */
	@Override
	public URI toUri() {
		return UriComponentsBuilder.fromPath("/contact-types/{id}").buildAndExpand(id).toUri();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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
