package com.revature.rideshare.user.controllers;

import javax.validation.Valid;

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
import com.revature.rideshare.user.beans.ResponseError;
import com.revature.rideshare.user.beans.User;

@RestController
public class UserController {
	@Autowired
	UserService userService;

	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> findById(@PathVariable("id") int id) {
		User user = userService.findById(id);
		return user == null
				? new ResponseError("User with ID " + id + " does not exist.").toResponseEntity(HttpStatus.NOT_FOUND)
				: ResponseEntity.ok(user);
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET, params = "email")
	public ResponseEntity<?> findByEmail(@RequestParam("email") String email) {
		User user = userService.findByEmail(email);
		return user == null ? new ResponseError("User with email " + email + " does not exist.")
				.toResponseEntity(HttpStatus.NOT_FOUND) : ResponseEntity.ok(user);
	}

	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public ResponseEntity<User> add(@RequestBody @Valid User user) {
		user.setId(0);
		User result = userService.save(user);
		if (result != null) {
			return new ResponseEntity<User>(result, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<User>(result, HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
	public ResponseEntity<User> update(@PathVariable("id") int id, @RequestBody @Valid User user) {
		user.setId(id);
		User result = userService.save(user);
		if (result != null) {
			return new ResponseEntity<User>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<User>(result, HttpStatus.CONFLICT);
		}
	}
}
