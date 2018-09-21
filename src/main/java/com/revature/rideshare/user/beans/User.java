package com.revature.rideshare.user.beans;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.revature.rideshare.user.services.CarService;
import com.revature.rideshare.user.services.ContactInfoService;
import com.revature.rideshare.user.services.OfficeService;
import com.revature.rideshare.user.services.UserRoleService;

@Entity
@Table(name = "USERS")
public class User implements UserDetails {
	private static final long serialVersionUID = 1L;

	@Autowired
	private transient UserRoleService userRoleService;

	@Autowired
	private transient OfficeService officeService;

	@Autowired
	private transient CarService carService;

	@Autowired
	private transient ContactInfoService contactInfoService;

	@Id
	@Column(name = "USER_ID")
	@SequenceGenerator(name = "userid", sequenceName = "userid")
	@GeneratedValue(generator = "userid", strategy = GenerationType.SEQUENCE)
	private int id;

	@Column(nullable = false, length = 25)
	private String firstName;

	@Column(nullable = false, length = 30)
	private String lastName;

	@Column(unique = true, nullable = false, length = 40)
	private String email;

	@JsonIgnore
	@Column(nullable = false, length = 70)
	private String password;

	@Column(length = 200)
	private String photoUrl;

	private boolean active;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "ROLE_ID", nullable = false)
	private UserRole role;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "OFFICE_ID", nullable = false)
	private Office office;

	private String address;

	@Column(columnDefinition = "DATE")
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
	private Date batchEnd;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "owner")
	private Set<Car> cars;

	@Column(length = 30)
	private String venmo;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
	private Set<ContactInfo> contactInfo;

	public User() {
		super();
	}

	public int getId() {
		return id;
	}

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

	/**
	 * Gets the user's role as a string.
	 * 
	 * @return the user's role as an uppercase string
	 */
	@JsonProperty("role")
	public String getRoleString() {
		return role.getType().toUpperCase();
	}

	/**
	 * Sets the user's role from a string value.
	 * 
	 * @param role the string representation of the role (case does not matter)
	 * @throws IllegalArgumentException if the role string does not correspond to a
	 *                                  role
	 */
	@JsonProperty("role")
	public void setRoleString(String role) {
		UserRole newRole = userRoleService.findByType(role);
		if (newRole == null) {
			throw new IllegalArgumentException(role + " is not a valid role.");
		}
		this.role = newRole;
	}

	/**
	 * Gets the office property as a link.
	 * 
	 * @return a link to the office
	 */
	@JsonProperty("office")
	public String getOfficeLink() {
		return UriComponentsBuilder.fromPath("/offices/{id}").buildAndExpand(office.getId()).toString();
	}

	/**
	 * Sets the office from a link string.
	 * 
	 * @param uri the URI linking to the office
	 */
	@JsonProperty("office")
	public void setOfficeLink(String uri) {
		AntPathMatcher matcher = new AntPathMatcher();
		int userId = Integer.parseInt(matcher.extractUriTemplateVariables("/offices/{id}", uri).get("id"));
		office = officeService.findById(userId);
	}

	/**
	 * Gets the cars property as a set of links.
	 * 
	 * @return a set of links to the user's cars
	 */
	@JsonProperty("cars")
	public Set<String> getCarsLinks() {
		return cars.stream()
				.map(car -> UriComponentsBuilder.fromPath("/cars/{id}").buildAndExpand(car.getId()).toString())
				.collect(Collectors.toSet());
	}

	/**
	 * Sets the cars from a set of link strings.
	 * 
	 * @param uris the set of URIs linking to the cars
	 */
	@JsonProperty("cars")
	public void setCarsLinks(Set<String> uris) {
		AntPathMatcher matcher = new AntPathMatcher();
		cars = uris.stream().map(uri -> {
			int id = Integer.parseInt(matcher.extractUriTemplateVariables("/cars/{id}", uri).get("id"));
			return carService.findById(id);
		}).collect(Collectors.toSet());
	}

	/**
	 * Gets the contactInfo property as a set of links.
	 * 
	 * @return a set of links to the user's contact info
	 */
	@JsonProperty("contactInfo")
	public Set<String> getContactInfoLinks() {
		return contactInfo.stream().map(
				info -> UriComponentsBuilder.fromPath("/contact-info/{id}").buildAndExpand(info.getId()).toString())
				.collect(Collectors.toSet());
	}

	/**
	 * Sets the contactInfo property from a set of link strings.
	 * 
	 * @param uris the set of URIs linking to the contact info
	 */
	@JsonProperty("contactInfo")
	public void setContactInfoLinks(Set<String> uris) {
		AntPathMatcher matcher = new AntPathMatcher();
		contactInfo = uris.stream().map(uri -> {
			int id = Integer.parseInt(matcher.extractUriTemplateVariables("/contact-info/{id}", uri).get("id"));
			return contactInfoService.findById(id);
		}).collect(Collectors.toSet());
	}

	// TODO Handle different user roles
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		GrantedAuthority auth = () -> "user";
		return Arrays.asList(auth);
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
}
