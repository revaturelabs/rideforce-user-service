package com.revature.rideforce.user.controllers;

import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.JOSEException;
import com.revature.rideforce.user.beans.RegistrationToken;
import com.revature.rideforce.user.beans.ResponseError;
import com.revature.rideforce.user.services.AuthenticationService;
import net.minidev.json.JSONObject;

@Lazy(true)
@RestController
@CrossOrigin(origins="*")
public class JWKController {
	@Autowired
	private JSONObject publicJWK;

	@Autowired
	private AuthenticationService as;

	@GetMapping("/.well-known/jwks.json")
	public JSONObject wellKnowns() {
		return publicJWK;
	}
	
	@PostMapping("/tokens/registration")
	public ResponseEntity<?> registrationToken(@Valid @RequestBody RegistrationToken rt) {
		try {
			return ResponseEntity.created(null).body("{ \"token\": \"" + as.createRegistrationToken(rt) + "\" }");
		} catch (JOSEException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseError("Unable to generate registration token.").toResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/tokens/registration")
	public ResponseEntity<?> registrationToken(@RequestBody String token) {
		System.out.println(as.processToken(token).getClaim("email").toString());
		return ResponseEntity.ok(Optional.ofNullable(as.processToken(token)).map(o -> o.toJSONObject().toJSONString()).orElse("INVALID TOKEN"));
	}
}
