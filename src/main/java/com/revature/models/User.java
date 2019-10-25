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
    @SequenceGenerator(sequenceName = "user_id_maker", name = "user_seq")
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
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "roles_users_JT", joinColumns = { @JoinColumn(name = "u_id") }, inverseJoinColumns = {
            @JoinColumn(name = "rid") })
    private List<Role> roles;
    
    /**
     * Where the user is currently located. See model {@link Location}.
     */
    @OneToOne(cascade = CascadeType.ALL)
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
    
}
