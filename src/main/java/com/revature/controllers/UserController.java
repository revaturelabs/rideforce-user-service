package com.revature.controllers;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.User;
import com.revature.services.LocationService;
import com.revature.services.UserService;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {
	/**
	 * The UserService is a service level class which contains all of the methods
	 * that are used for the User model in this application.
	 * 
	 * @see UserService
	 * @see User
	 */
	@Autowired
	UserService us;

	@Autowired
	LocationService ls;

	/**
	 * This method is called when a get request is sent to the backend and the URI
	 * is "/users".
	 * 
	 * This method returns all of the Users in the users table through the JSON
	 * media type.
	 * 
	 * @return a list of all the users in the users table
	 * @see UserService
	 */
	@GetMapping
	public List<User> getAllUsers() {
		return us.getAllUsers();
	}

	@GetMapping(value = "/drivers", params = "isActive")
	public List<User> getAllActiveDrivers(@RequestParam(required = false) Boolean isActive) {
		if (isActive != null) {
			if (isActive == true) {
				return us.getAllActiveDrivers();
			}
			else {
				return us.getAllInactiveDrivers();
			}
		}
		return us.getAllDrivers();
	}

	/**
	 * This method is called when a get request is sent to the backend and the URI
	 * is "/users/{uid}"
	 * 
	 * This method returns a User which has the ID specified in the URI.
	 * 
	 * @param uid the User ID of the user you would like to find.
	 * @return the user which has the specified ID.
	 * @see UserService
	 */
	@GetMapping(value = "{uid}")
	public User getUserById(@PathVariable("uid") int uid) {
		return us.getUserById(uid);
	}

	/**
	 * This method is called when a get request is sent to the backend and the URI
	 * is "/users?email={email}"
	 * 
	 * This method returns a User which had the email specified in the URI
	 * 
	 * @param email the User email of the user you would like to find.
	 * @return the User which has the specified email
	 * @see UserService
	 */
	@GetMapping(params = "email")
	public User getUserByEmail(@RequestParam("email") @NotEmpty String email) {
		return us.getUserByEmail(email);
	}

	@PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
	public User userLogin(@RequestBody User u, HttpServletResponse response) {
		User user = us.userLogin(u.getEmail(), u.getPassword());
		if (user == null) {
			response.setStatus(401);
			return null;
		}
		return user;

	}

	/**
	 * This method is called when a post request is sent to he backend and the URI
	 * is "/users" with a User object in the request body represented as a JSON
	 * media type.
	 * 
	 * This method is able to save a User entity to the database.
	 * 
	 * @param user the User which you would like to make a record for in the
	 *             database.
	 */
	@PostMapping(consumes = "application/json", produces = "application/json")
	public User createUser(@RequestBody User user, HttpServletResponse response) {
		user.setLocation(user.getLocation());
		if (user.getLocation() == null) {
			response.setStatus(400);
			return null;
		}
		User newUser = us.createUser(user);
		if (newUser == null) {
			response.setStatus(400);
			return null;
		}
		return newUser;

	}

	/**
	 * This method is called when a put request is sent to the backend and the URI
	 * is "/users" with a User object in the request body represented as a JSON
	 * media type.
	 * 
	 * This method is able to update a User entity in the database.
	 * 
	 * @param user the User which you would like to update in the database.
	 */
	@PutMapping(value = "/{uid}", consumes = "application/json", produces = "application/json")
	public User updateUser(@PathVariable("uid") int uid, @RequestBody User user, HttpServletResponse response) {
		user.setUid(uid);
		
		user.setLocation(ls.updateLocation(user.getLocation()));
		if (user.getLocation() == null) {
			response.setStatus(400);
			return null;
		}

		User updatedUser = us.updateUser(user);
		if (updatedUser == null) {
			response.setStatus(400);
			return null;
		}

		return updatedUser;

	}

	/**
	 * This method is called when a delete request is sent to the backend and the
	 * URI is "/users/{uid}"
	 * 
	 * This method is used to remove a User record from the database.
	 * 
	 * @param uid The User ID of the User you would like to delete from the
	 *            database.
	 */
	@DeleteMapping(value = "{uid}")
	public boolean deleteUser(@PathVariable("uid") int uid) {
		return us.deleteUser(us.getUserById(uid));
	}
}
