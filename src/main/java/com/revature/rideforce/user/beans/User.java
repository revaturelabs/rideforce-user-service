package com.revature.rideforce.user.beans;

import java.net.URI;
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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revature.rideforce.user.json.CarLinkResolver;
import com.revature.rideforce.user.json.ContactInfoLinkResolver;
import com.revature.rideforce.user.json.JsonEnumLike;
import com.revature.rideforce.user.json.JsonLink;
import com.revature.rideforce.user.json.Linkable;
import com.revature.rideforce.user.json.OfficeLinkResolver;
import com.revature.rideforce.user.json.UserRoleResolver;

/**

  Encapsulates state information of the end user. 


  */

@Entity
@Table(name = "USERS")
public class User implements UserDetails, Identifiable, Linkable {
	private static final long serialVersionUID = 1L;

	@Id
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
	private String email;

	@JsonIgnore
	@Column(nullable = false, length = 70)
	private String password;

	@Column(length = 200)
	@Size(max = 200)
	private String photoUrl;

	@Column(length = 200)
	@Size(max = 200)
	private String bio;

  /**
    indicates if a 
    */
	private boolean active;

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
	}

	public User(int id, @NotEmpty String firstName, @NotEmpty String lastName, @NotEmpty String email, String password,
			@Size(max = 200) String photoUrl, @Size(max = 200) String bio, boolean active,
			@NotNull @Valid UserRole role, @NotNull @Valid Office office, @NotEmpty String address, Date batchEnd,
			@NotNull @Valid Set<Car> cars, String venmo, @NotNull @Valid Set<ContactInfo> contactInfo) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.photoUrl = photoUrl;
		this.bio = bio;
		this.active = active;
		this.role = role;
		this.office = office;
		this.address = address;
		this.batchEnd = batchEnd;
		this.cars = cars;
		this.venmo = venmo;
		this.contactInfo = contactInfo;
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
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoURL) {
		this.photoUrl = photoURL;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
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

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		return active;
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
}
