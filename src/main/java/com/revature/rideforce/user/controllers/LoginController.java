package com.revature.rideforce.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.rideforce.user.beans.ResponseError;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.services.AuthenticationService;

@Lazy(true)
@RestController
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
}
