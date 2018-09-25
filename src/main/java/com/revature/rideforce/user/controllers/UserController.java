package com.revature.rideforce.user.controllers;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.rideforce.user.beans.Office;
import com.revature.rideforce.user.beans.PasswordChangeRequest;
import com.revature.rideforce.user.beans.ResponseError;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.beans.UserRegistrationInfo;
import com.revature.rideforce.user.beans.UserRole;
import com.revature.rideforce.user.exceptions.EntityConflictException;
import com.revature.rideforce.user.exceptions.InvalidRegistrationKeyException;
import com.revature.rideforce.user.exceptions.PermissionDeniedException;
import com.revature.rideforce.user.services.AuthenticationService;
import com.revature.rideforce.user.services.OfficeService;
import com.revature.rideforce.user.services.UserRoleService;
import com.revature.rideforce.user.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	UserService userService;

	@Autowired
	AuthenticationService authenticationService;

	@Autowired
	OfficeService officeService;

	@Autowired
	UserRoleService userRoleService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> findAll() {
		try {
			return ResponseEntity.ok(userService.findAll());
		} catch (PermissionDeniedException e) {
			return new ResponseError(e).toResponseEntity(HttpStatus.FORBIDDEN);
		}
	}

	@GetMapping(params = "email", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> findByEmail(@RequestParam("email") @NotEmpty String email) {
		try {
			User user = userService.findByEmail(email);
			return user == null ? new ResponseError("User with email " + email + " does not exist.")
					.toResponseEntity(HttpStatus.NOT_FOUND) : ResponseEntity.ok(user);
		} catch (PermissionDeniedException e) {
			return new ResponseError(e).toResponseEntity(HttpStatus.FORBIDDEN);
		}
	}

	@GetMapping(params = { "office", "role" }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> findByOfficeAndRole(@RequestParam("office") int officeId,
			@RequestParam("role") String roleString) {
		try {
			Office office = officeService.findById(officeId);
			if (office == null) {
				return new ResponseError("Office with ID " + officeId + " does not exist.")
						.toResponseEntity(HttpStatus.BAD_REQUEST);
			}
			UserRole role = userRoleService.findByType(roleString);
			if (role == null) {
				return new ResponseError(roleString + " is not a valid user role.")
						.toResponseEntity(HttpStatus.BAD_REQUEST);
			}

			return ResponseEntity.ok(userService.findByOfficeAndRole(office, role));
		} catch (PermissionDeniedException e) {
			return new ResponseError(e).toResponseEntity(HttpStatus.FORBIDDEN);
		}
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> findById(@PathVariable("id") int id) {
		try {
			User user = userService.findById(id);
			return user == null ? new ResponseError("User with ID " + id + " does not exist.")
					.toResponseEntity(HttpStatus.NOT_FOUND) : ResponseEntity.ok(user);
		} catch (PermissionDeniedException e) {
			return new ResponseError(e).toResponseEntity(HttpStatus.FORBIDDEN);
		}
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> add(@RequestBody @Valid UserRegistrationInfo registration) {
		try {
			User created = authenticationService.register(registration);
			return ResponseEntity.created(created.toUri()).body(created);
		} catch (InvalidRegistrationKeyException | PermissionDeniedException e) {
			return new ResponseError(e).toResponseEntity(HttpStatus.FORBIDDEN);
		} catch (EntityConflictException e) {
			return new ResponseError(e).toResponseEntity(HttpStatus.CONFLICT);
		}
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> save(@PathVariable("id") int id, @RequestBody @Valid User user) {
		user.setId(id);
		try {
			return ResponseEntity.ok(userService.save(user));
		} catch (EntityConflictException e) {
			return new ResponseError(e).toResponseEntity(HttpStatus.CONFLICT);
		} catch (PermissionDeniedException e) {
			return new ResponseError(e).toResponseEntity(HttpStatus.FORBIDDEN);
		}
	}

	@PutMapping(value = "/{id}/password", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ResponseError> updatePassword(@PathVariable("id") int id,
			@RequestBody @Valid PasswordChangeRequest request) {
		try {
			User found = userService.findById(id);
			if (found == null) {
				return new ResponseError("User not found.").toResponseEntity(HttpStatus.NOT_FOUND);
			}
			userService.updatePassword(found, request.getOldPassword(), request.getNewPassword());
			return ResponseEntity.ok().build();
		} catch (PermissionDeniedException e) {
			return new ResponseError(e).toResponseEntity(HttpStatus.FORBIDDEN);
		}
	}
}
