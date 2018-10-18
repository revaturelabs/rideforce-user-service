package com.revature.rideforce.user.controllers;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.rideforce.user.beans.Office;
import com.revature.rideforce.user.beans.ResponseError;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.beans.UserRegistrationInfo;
import com.revature.rideforce.user.beans.UserRole;
import com.revature.rideforce.user.beans.forms.ChangeUserModel;
import com.revature.rideforce.user.exceptions.EmptyPasswordException;
import com.revature.rideforce.user.exceptions.EntityConflictException;
import com.revature.rideforce.user.exceptions.InvalidRegistrationKeyException;
import com.revature.rideforce.user.exceptions.PasswordRequirementsException;
import com.revature.rideforce.user.exceptions.PermissionDeniedException;
import com.revature.rideforce.user.services.AuthenticationService;
import com.revature.rideforce.user.services.OfficeService;
import com.revature.rideforce.user.services.UserRoleService;
import com.revature.rideforce.user.services.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Lazy(true)
@RequestMapping("/users")
public class UserController {
	static final String DNE = " does not exist.";
	
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
			User user = userService.findByEmail(email.toLowerCase());  //make the email to be looked for lower case to match the case of our db

			return user == null ? new ResponseError("User with email " + email + DNE)
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
				return new ResponseError("Office with ID " + officeId + DNE)
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
			return user == null ? new ResponseError("User with ID " + id + DNE)
					.toResponseEntity(HttpStatus.NOT_FOUND) : ResponseEntity.ok(user);
		} catch (PermissionDeniedException e) {
			return new ResponseError(e).toResponseEntity(HttpStatus.FORBIDDEN);
		}
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> add(@RequestBody @Valid UserRegistrationInfo registration) {
		try {
			User user = registration.getUser(); //change the user's email to lowercase then save user back to registration info
			user.setEmail(user.getUsername().toLowerCase());
			registration.setUser(user);
			log.info("Received Registration in RequestBody: {}", registration);
			User created = authenticationService.register(registration);
			return ResponseEntity.created(created.toUri()).body(created);
		} catch (InvalidRegistrationKeyException | PermissionDeniedException e) {
			return new ResponseError(e).toResponseEntity(HttpStatus.FORBIDDEN);
		} catch (EntityConflictException e) {
			return new ResponseError(e).toResponseEntity(HttpStatus.CONFLICT);
		} catch (EmptyPasswordException e) {
			return new ResponseError(e).toResponseEntity(HttpStatus.LENGTH_REQUIRED);
		} catch (PasswordRequirementsException e) {
			return new ResponseError(e).toResponseEntity(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> save(@PathVariable("id") int id, @RequestBody ChangeUserModel changedUserModel) throws PermissionDeniedException, EntityConflictException {
		
		User user = userService.findById(id);
		changedUserModel.changeUser(user); 		//set the changes to the user based on the provided form 
		UserRole role = userRoleService.findByType(changedUserModel.getRole());

		if(role != null) {
			user.setRole(role);
		}
		
		try {
			return ResponseEntity.ok(userService.save(user)); 		//update user
		} catch (EntityConflictException e) {
			return new ResponseError(e).toResponseEntity(HttpStatus.CONFLICT);
		} catch (PermissionDeniedException e) {
			return new ResponseError(e).toResponseEntity(HttpStatus.FORBIDDEN);
		}
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") int id) throws PermissionDeniedException {
		User userToDelete = userService.findById(id); //throw permission denied exception if not logged in
		if(userToDelete != null)
			try {
				userService.deleteUser(userToDelete);
				return ResponseEntity.ok(userToDelete);
			} catch (PermissionDeniedException e) {
				return new ResponseError(e).toResponseEntity(HttpStatus.FORBIDDEN);
			}
		else {
			return (ResponseEntity<?>) ResponseEntity.notFound();}
	}
}
