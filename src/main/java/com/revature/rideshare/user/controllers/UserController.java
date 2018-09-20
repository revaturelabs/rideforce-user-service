package com.revature.rideshare.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.rideshare.services.UserService;
import com.revature.rideshare.user.beans.User;
import com.revature.rideshare.user.beans.UserRegistrationInfo;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/users", method=RequestMethod.GET)
	public ResponseEntity<User> getUserByUsername(@RequestParam User user)
	{
		User result = userService.findByEmail(user.getEmail());
		if (result != null)
		{
			return new ResponseEntity<User>(result, HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<User>(result, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value="/users/{id}", method=RequestMethod.GET)
	public ResponseEntity<User> getUserById(@PathVariable User user)
	{
		User result = userService.findById(user.getId());
		if (result != null)
		{
			return new ResponseEntity<User>(result, HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<User>(result, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value="/users", method=RequestMethod.POST)
	public ResponseEntity<User> addUser(@RequestBody UserRegistrationInfo userInfo)
	{
		User result = userService.register(userInfo);
		if (result != null)
		{
			return new ResponseEntity<User>(result, HttpStatus.CREATED);
		}
		else
		{
			return new ResponseEntity<User>(result, HttpStatus.CONFLICT);
		}
	}
	
	
	@RequestMapping(value="/users/{id}", method=RequestMethod.PUT)
	public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User user)
	{
		User result = userService.save(user);
		if (result != null)
		{
			return new ResponseEntity<User>(result, HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<User>(result, HttpStatus.CONFLICT);
		}
	}
	
}
