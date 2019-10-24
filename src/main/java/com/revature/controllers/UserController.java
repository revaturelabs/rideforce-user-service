package com.revature.controllers;

import java.util.List;

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
	@PostMapping(consumes = "application/json")
	public void createUser(@RequestBody User user) {
		us.createUser(user);
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
	@PutMapping(consumes = "application/json")
	public void updateUser(@RequestBody User user) {
		us.updateUser(user);
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
	public void deleteUser(@PathVariable("uid") int uid) {
		us.deleteUser(us.getUserById(uid));
	}
}
