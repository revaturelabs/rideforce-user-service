package com.revature.rideforce.user.controllers;

import java.net.URISyntaxException;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.slf4j.Logger;
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

import com.amazonaws.services.cognitoidp.model.UsernameExistsException;
import com.revature.rideforce.user.beans.Office;
import com.revature.rideforce.user.beans.ResponseError;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.beans.UserRegistration;
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

@Lazy(true)
@RestController
@RequestMapping("/users")
public class UserController {
	static final String DNE = " does not exist.";
	
	@Autowired
	private Logger log;
	
	@Autowired
	private UserService us;

	@Autowired
	private AuthenticationService as;

	@Autowired
	private OfficeService os;

	@Autowired
	private UserRoleService urs;

	@GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> findAll() {
		try {
			return ResponseEntity.ok(us.findAll());
		} catch (PermissionDeniedException e) {
			return new ResponseError(e).toResponseEntity(HttpStatus.FORBIDDEN);
		}
	}

	@GetMapping(params = "email", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> findByEmail(@RequestParam("email") @NotEmpty String email) {
		try {
			User user = us.findByEmail(email);  //make the email to be looked for lower case to match the case of our db

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
			Office office = os.findById(officeId);
			if (office == null) {
				return new ResponseError("Office with ID " + officeId + DNE)
						.toResponseEntity(HttpStatus.BAD_REQUEST);
			}
			UserRole role = urs.findByType(roleString);
			if (role == null) {
				return new ResponseError(roleString + " is not a valid user role.")
						.toResponseEntity(HttpStatus.BAD_REQUEST);
			}

			return ResponseEntity.ok(us.findByOfficeAndRole(office, role));
		} catch (PermissionDeniedException e) {
			return new ResponseError(e).toResponseEntity(HttpStatus.FORBIDDEN);
		}
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> findById(@PathVariable("id") int id) {
		try {
			User user = us.findById(id);
			return user == null ? new ResponseError("User with ID " + id + DNE)
					.toResponseEntity(HttpStatus.NOT_FOUND) : ResponseEntity.ok(user);
		} catch (PermissionDeniedException e) {
			return new ResponseError(e).toResponseEntity(HttpStatus.FORBIDDEN);
		}
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> add(@RequestBody @Valid UserRegistration registration) throws URISyntaxException {
		try {
			User created = as.register(registration);
			return ResponseEntity.created(created.toUri()).body("{ \"message\": \"Registration successful! Please check your email for an activation link.\"}");
		} catch (InvalidRegistrationKeyException | PermissionDeniedException e) {
			return new ResponseError(e).toResponseEntity(HttpStatus.FORBIDDEN);
		} catch (EntityConflictException | UsernameExistsException e) {
			return new ResponseError(e).toResponseEntity(HttpStatus.CONFLICT);
		} catch (EmptyPasswordException e) {
			return new ResponseError(e).toResponseEntity(HttpStatus.LENGTH_REQUIRED);
		} catch (PasswordRequirementsException e) {
			return new ResponseError(e).toResponseEntity(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> save(@PathVariable("id") int id, @RequestBody ChangeUserModel changedUserModel) throws PermissionDeniedException, EntityConflictException {
		
		User user = us.findById(id);
		changedUserModel.changeUser(user); 		//set the changes to the user based on the provided form 
		UserRole role = urs.findByType(changedUserModel.getRole());

		if(role != null) {
			user.setRole(role);
		}
		
		try {
			System.out.println(changedUserModel);
			return ResponseEntity.ok(us.save(user)); 		//update user
		} catch (EntityConflictException e) {
			return new ResponseError(e).toResponseEntity(HttpStatus.CONFLICT);
		} catch (PermissionDeniedException e) {
			return new ResponseError(e).toResponseEntity(HttpStatus.FORBIDDEN);
		}
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") int id) throws PermissionDeniedException {
		User userToDelete = us.findById(id); //throw permission denied exception if not logged in
		if(userToDelete != null)
			try {
				us.deleteUser(userToDelete);
				return ResponseEntity.ok(userToDelete);
			} catch (PermissionDeniedException e) {
				return new ResponseError(e).toResponseEntity(HttpStatus.FORBIDDEN);
			}
		else {
			return (ResponseEntity<?>) ResponseEntity.notFound();}
	}
}
