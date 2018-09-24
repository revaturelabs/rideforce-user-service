package com.revature.rideforce.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.rideforce.user.security.RegistrationTokenProvider;

@RestController
@RequestMapping("/registration-key")
public class RegistrationKeyController {
	@Autowired
	private RegistrationTokenProvider tokenProvider;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> get() {
		// TODO: make sure only trainers and admins can create these.
		return ResponseEntity.ok('"' + tokenProvider.generateToken() + '"');
	}
}
