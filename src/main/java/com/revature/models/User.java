package com.revature.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Represents a User's features, to be mapped in the database.
 * 
 * @author Kush Patel, Coel DeLeon, Clinton Yoos
 * 
 * @version 1908sdet.0.1
 *
 */
@Entity
@Table(name="users")
public class User {
	
    /**
     * Basic primary key of users in the table from the database
     */
    @Id
    @SequenceGenerator(sequenceName = "user_id_maker", name = "user_seq", allocationSize = 1)
    @GeneratedValue(generator = "user_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "u_id")
    private int uid;
    
    /**
     * Email of Revature employee; used for login
     */
    @Column(unique = true)
    private String email;
    @Column
    private String password;
    @Column
    private String fname;
    @Column
    private String lname;
    
    /**
     * Determines whether the user is a driver, rider, or both. See model {@link Role}.
     */
    @ManyToMany
    @JoinTable(name = "roles_users_JT", joinColumns = { @JoinColumn(name = "u_id") }, inverseJoinColumns = {
            @JoinColumn(name = "rid") })
    private List<Role> roles;
    
    /**
     * Where the user is currently located. See model {@link Location}.
     */
    @OneToOne
    @JoinColumn(name = "lid")
    Location location;
    
    /**
     * Mainly used to determine whether or not the driver is currently able to drive people.
     */
    @Column(name = "is_active")
    private boolean isActive;
    
    //No arg constructor.
    public User() {
        super();
    }
    /**
     * Default no ID constructor.
     * 
     * @param email         Email of Revature employee; used for login
     * @param password      
     * @param fname
     * @param lname
     * @param roles         Determines whether the user is a driver, rider, or both. See model {@link Role}.
     * @param location      Where the user is currently located. See model {@link Location}.
     * @param isActive     Mainly used to determine whether or not the driver is currently able to drive people.
     */
    public User(String email, String password, String fname, String lname, List<Role> roles, Location location,
            boolean isActive) {
        super();
        this.email = email;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        this.roles = roles;
        this.location = location;
        this.isActive = isActive;
    }
    
    /**
     * Default constructor w/ ID.
     * 
     * @param uid           Basic primary key of users in the table from the database
     * @param email         Email of Revature employee; used for login
     * @param password      
     * @param fname
     * @param lname
     * @param roles         Determines whether the user is a driver, rider, or both. See model {@link Role}.
     * @param location      Where the user is currently located. See model {@link Location}.
     * @param isActive     Mainly used to determine whether or not the driver is currently able to drive people.
     */
    public User(int uid, String email, String password, String fname, String lname, List<Role> roles, Location location,
            boolean isActive) {
        super();
        this.uid = uid;
        this.email = email;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        this.roles = roles;
        this.location = location;
        this.isActive = isActive;
    }
    
    //Begin public getters and setters
    public int getUid() {
        return uid;
    }
    public void setUid(int uid) {
        this.uid = uid;
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
    public String getFname() {
        return fname;
    }
    public void setFname(String fname) {
        this.fname = fname;
    }
    public String getLname() {
        return lname;
    }
    public void setLname(String lname) {
        this.lname = lname;
    }
    public List<Role> getRoles() {
        return roles;
    }
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }
    public boolean getIsActive() {
        return isActive;
    }
    public void setIs_active(boolean isActive) {
        this.isActive = isActive;
    }
    //End public getters and setters
    
    @Override
    public String toString() {
        return "User [uid=" + uid + ", email=" + email + ", password=" + password + ", fname=" + fname + ", lname="
                + lname + ", is_active=" + isActive + "]";
    }
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((fname == null) ? 0 : fname.hashCode());
		result = prime * result + (isActive ? 1231 : 1237);
		result = prime * result + ((lname == null) ? 0 : lname.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
		result = prime * result + uid;
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
		if (email == null) {
			if (other.email != null)
				return false;
		}
		else if (!email.equals(other.email))
			return false;
		if (fname == null) {
			if (other.fname != null)
				return false;
		}
		else if (!fname.equals(other.fname))
			return false;
		if (isActive != other.isActive)
			return false;
		if (lname == null) {
			if (other.lname != null)
				return false;
		}
		else if (!lname.equals(other.lname))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		}
		else if (!location.equals(other.location))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		}
		else if (!password.equals(other.password))
			return false;
		if (roles == null) {
			if (other.roles != null)
				return false;
		}
		else if (!roles.equals(other.roles))
			return false;
		if (uid != other.uid)
			return false;
		return true;
	}
    
    
}
