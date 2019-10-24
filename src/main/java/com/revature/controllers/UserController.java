package com.revature.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.User;
import com.revature.services.UserService;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserService us;
	
	@GetMapping
	public List<User> getAllUsers(){
		
		return us.getAllUsers();
	}

	@GetMapping(value = "{uid}")
	public User getUserById(@PathVariable("uid")int uid) {
		return us.getUserById(uid);
	}
	
	@GetMapping(value = "{email}")
	public User getUserByEmail(@PathVariable("email")String email) {
		return us.getUserByEmail(email);
	}
	
	@PostMapping(consumes = "application/json")
	public void createUser(@RequestBody User user) {
		us.createUser(user);
	}
	
	@PutMapping(consumes="application/json")
	public void updateUser(@RequestBody User user) {
		us.updateUser(user);
	}
	
	@DeleteMapping(value="{uid}")
	public void deleteUser(@PathVariable("uid")int uid) {
		us.deleteUser(us.getUserById(uid));
	}
	
}
