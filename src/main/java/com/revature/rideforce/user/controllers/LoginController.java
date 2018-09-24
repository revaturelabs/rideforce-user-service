package com.revature.rideforce.user.controllers;

import javax.validation.Valid;

//import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.rideforce.user.beans.ResponseError;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.beans.UserCredentials;
import com.revature.rideforce.user.exceptions.InvalidCredentialsException;
import com.revature.rideforce.user.services.UserService;

@RestController
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<?> getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth == null ? new ResponseError("Not logged in.").toResponseEntity(HttpStatus.FORBIDDEN)
				: ResponseEntity.status(HttpStatus.FOUND).location(((User) auth.getPrincipal()).toUri()).build();
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> login(@RequestBody @Valid UserCredentials credentials) {
		try {
			return ResponseEntity.ok('"' + userService.authenticate(credentials) + '"');
		} catch (InvalidCredentialsException e) {
			return new ResponseError(e).toResponseEntity(HttpStatus.FORBIDDEN);
		}
	}
}
