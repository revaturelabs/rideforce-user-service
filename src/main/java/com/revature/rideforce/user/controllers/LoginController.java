package com.revature.rideforce.user.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.rideforce.user.beans.ResponseError;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.beans.UserCredentials;
import com.revature.rideforce.user.exceptions.DisabledAccountException;
import com.revature.rideforce.user.exceptions.InvalidCredentialsException;
import com.revature.rideforce.user.services.AuthenticationService;

@RestController
@Lazy(true)
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private AuthenticationService authenticationService;

	@GetMapping
	public ResponseEntity<?> getCurrentUser() {
		User currentUser = authenticationService.getCurrentUser();
		return currentUser == null ? new ResponseError("Not logged in.").toResponseEntity(HttpStatus.FORBIDDEN)
				: ResponseEntity.ok(currentUser);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> login(@RequestBody @Valid UserCredentials credentials) {
		try {
			credentials.setEmail(credentials.getEmail().toLowerCase());  //email case shouldnt matter during login
			return ResponseEntity.ok('"' + authenticationService.authenticate(credentials) + '"');
		} catch (InvalidCredentialsException e) {
			return new ResponseError(e).toResponseEntity(HttpStatus.FORBIDDEN);
		} catch (DisabledAccountException e) {
			return new ResponseError(e).toResponseEntity(HttpStatus.LOCKED);
		}
	}
	
}
