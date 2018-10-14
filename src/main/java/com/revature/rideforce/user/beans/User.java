package com.revature.rideforce.user.beans;

import java.io.Serializable;
import java.net.URI;
import java.sql.Time;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.revature.rideforce.user.exceptions.EmptyPasswordException;
import com.revature.rideforce.user.json.CarLinkResolver;
import com.revature.rideforce.user.json.ContactInfoLinkResolver;
import com.revature.rideforce.user.json.JsonEnumLike;
import com.revature.rideforce.user.json.JsonLink;
import com.revature.rideforce.user.json.Linkable;
import com.revature.rideforce.user.json.OfficeLinkResolver;
import com.revature.rideforce.user.json.UserRoleResolver;
import com.revature.rideforce.user.security.PasswordSecurityUtil;

/**

  Encapsulates state information of the end user. 


  */

@Entity
@Table(name = "USERS")
public class User implements UserDetails, Identifiable, Linkable, Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Min(1)
	@Column(name = "USER_ID")
	@SequenceGenerator(name = "userid", sequenceName = "userid")
	@GeneratedValue(generator = "userid", strategy = GenerationType.SEQUENCE)
	private int id;

	@Column(nullable = false, length = 25)
	@NotEmpty
	private String firstName;

	@Column(nullable = false, length = 30)
	@NotEmpty
	private String lastName;

	@Column(unique = true, nullable = false, length = 40)
	@NotEmpty
	@Email
	private String email;

	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(nullable = false, length = 70)
	@NotEmpty
	private String password;

	@Column(length = 200)
	@Size(max = 200)
	private String photoUrl;

	@Column(length = 255)   //255 size as requested by angular team ;)
	@Size(max = 255)
	private String bio;

	@Column(name="ACTIVE")
	@JsonProperty
	private String active = "ACTIVE"; //default, other values can be "INACTIVE" for user choosing to deactivate, or "DISABLED" for admin disabling 

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ROLE_ID", nullable = false)
	@NotNull
	@Valid
	@JsonEnumLike(UserRoleResolver.class)
	private UserRole role;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "OFFICE_ID", nullable = false)
	@NotNull
	@Valid
	@JsonLink(OfficeLinkResolver.class)
	private Office office;

	@Column(nullable = false)
	@NotEmpty
	private String address;
	
	@Column()
//	@Temporal(TemporalType.DATE)
//	@Temporal(TemporalType.TIMESTAMP)   //  If this is put on, will say @Temporal should only be put on a Date or Calendar thing
//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
	private Time startTime = Time.valueOf("09:00:00");    			//when the class starts every day
	
	@Column(columnDefinition = "DATE")
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
	private Date batchEnd;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "owner")
	@NotNull
	@Valid
	@JsonLink(CarLinkResolver.class)
	private Set<Car> cars;

	@Column(length = 30)
	private String venmo;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
	@NotNull
	@Valid
	@JsonLink(ContactInfoLinkResolver.class)
	private Set<ContactInfo> contactInfo;
	
	public User() {
		super();
		this.role = new UserRole();
		this.office = new Office();
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email.toLowerCase();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) throws EmptyPasswordException { 
		//changing the place to encode because this is more modular than encoding in the Application.class, updateUser controller view, and loginrecovery reset password view
		if(password.equals(""))
			throw new EmptyPasswordException();
		this.password = PasswordSecurityUtil.getPasswordEncoder().encode(password); //made the class because as a member of User, gave me errors in LoginRecoveryControllerTest...null pointer exceptions, idk
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoURL) {
		this.photoUrl = photoURL;
	}

	public String isActive() {  //getter..... lol bad naming
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getBatchEnd() {
		return batchEnd;
	}

	public void setBatchEnd(Date batchEnd) {
		this.batchEnd = batchEnd;
	}

	public String getVenmo() {
		return venmo;
	}

	public void setVenmo(String venmo) {
		this.venmo = venmo;
	}

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {   //to provide a time, can do Time.valueOf("hh:mm:ss");
		this.startTime = startTime;
	}

	@JsonIgnore
	public boolean isAdmin() {
		return getAuthorities().stream().filter(auth -> auth.getAuthority().equalsIgnoreCase("admin")).findAny()
				.isPresent();
	}

	@JsonIgnore
	public boolean isTrainer() {
		return getAuthorities().stream().filter(auth -> auth.getAuthority().equalsIgnoreCase("trainer")).findAny()
				.isPresent();
	}

	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		GrantedAuthority auth = () -> this.getRole().toEnumString();
		return Arrays.asList(auth);
	}

	@Override
	@JsonIgnore
	public String getUsername() {
		return email;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return true;
	}

	public Set<Car> getCars() {
		return cars;
	}

	public void setCars(Set<Car> cars) {
		this.cars = cars;
	}

	public Set<ContactInfo> getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(Set<ContactInfo> contactInfo) {
		this.contactInfo = contactInfo;
	}

  /**
    @return endpoint where this <code>User</code>'s information can be accessed
    */
	@Override
	public URI toUri() {
		return UriComponentsBuilder.fromPath("/users/{id}").buildAndExpand(id).toUri();
	}

	

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((active == null) ? 0 : active.hashCode());
	result = prime * result + ((address == null) ? 0 : address.hashCode());
	result = prime * result + ((batchEnd == null) ? 0 : batchEnd.hashCode());
	result = prime * result + ((bio == null) ? 0 : bio.hashCode());
	result = prime * result + ((cars == null) ? 0 : cars.hashCode());
	result = prime * result + ((contactInfo == null) ? 0 : contactInfo.hashCode());
	result = prime * result + ((email == null) ? 0 : email.hashCode());
	result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
	result = prime * result + id;
	result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
	result = prime * result + ((office == null) ? 0 : office.hashCode());
	result = prime * result + ((password == null) ? 0 : password.hashCode());
	result = prime * result + ((photoUrl == null) ? 0 : photoUrl.hashCode());
	result = prime * result + ((role == null) ? 0 : role.hashCode());
	result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
	result = prime * result + ((venmo == null) ? 0 : venmo.hashCode());
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
	User other = (User) obj;
	if (active == null) {
		if (other.active != null)
			return false;
	} else if (!active.equals(other.active))
		return false;
	if (address == null) {
		if (other.address != null)
			return false;
	} else if (!address.equals(other.address))
		return false;
	if (batchEnd == null) {
		if (other.batchEnd != null)
			return false;
	} else if (!batchEnd.equals(other.batchEnd))
		return false;
	if (bio == null) {
		if (other.bio != null)
			return false;
	} else if (!bio.equals(other.bio))
		return false;
	if (cars == null) {
		if (other.cars != null)
			return false;
	} else if (!cars.equals(other.cars))
		return false;
	if (contactInfo == null) {
		if (other.contactInfo != null)
			return false;
	} else if (!contactInfo.equals(other.contactInfo))
		return false;
	if (email == null) {
		if (other.email != null)
			return false;
	} else if (!email.equals(other.email))
		return false;
	if (firstName == null) {
		if (other.firstName != null)
			return false;
	} else if (!firstName.equals(other.firstName))
		return false;
	if (id != other.id)
		return false;
	if (lastName == null) {
		if (other.lastName != null)
			return false;
	} else if (!lastName.equals(other.lastName))
		return false;
	if (office == null) {
		if (other.office != null)
			return false;
	} else if (!office.equals(other.office))
		return false;
	if (password == null) {
		if (other.password != null)
			return false;
	} else if (!password.equals(other.password))
		return false;
	if (photoUrl == null) {
		if (other.photoUrl != null)
			return false;
	} else if (!photoUrl.equals(other.photoUrl))
		return false;
	if (role == null) {
		if (other.role != null)
			return false;
	} else if (!role.equals(other.role))
		return false;
	if (startTime == null) {
		if (other.startTime != null)
			return false;
	} else if (!startTime.equals(other.startTime))
		return false;
	if (venmo == null) {
		if (other.venmo != null)
			return false;
	} else if (!venmo.equals(other.venmo))
		return false;
	return true;
}

@Override
public String toString() {
	return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", password="
			+ password + ", photoUrl=" + photoUrl + ", bio=" + bio + ", active=" + active + ", role=" + role
			+ ", office=" + office + ", address=" + address + ", startTime=" + startTime + ", batchEnd=" + batchEnd
			+ ", cars=" + cars + ", venmo=" + venmo + ", contactInfo=" + contactInfo + "]";
}

@Override
public boolean isEnabled() {
	// TODO Auto-generated method stub
	return false;
}

	

	
	
}
