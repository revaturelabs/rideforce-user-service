package com.revature.rideshare.user.controllers;

import java.util.Arrays;

import javax.validation.Valid;

//import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.rideshare.security.JwtProvider;
import com.revature.rideshare.services.UserService;
import com.revature.rideshare.user.beans.User;

@RestController
@RequestMapping("/login")
@CrossOrigin
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtProvider tokenProvider;
	
	

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> login(@RequestBody @Valid UserCredentials credentials) {
        
        // Login successful; generate a token.
        String token = tokenProvider.generateToken(999);
        return ResponseEntity.ok('"' + token + '"');
    }
	
}
