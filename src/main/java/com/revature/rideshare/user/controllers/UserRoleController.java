package com.revature.rideshare.user.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.rideshare.user.beans.ResponseError;
import com.revature.rideshare.user.beans.UserRole;
import com.revature.rideshare.user.exceptions.DuplicateRoleException;
import com.revature.rideshare.user.services.UserRoleService;

@RestController
@RequestMapping("/roles")
public class UserRoleController {
	@Autowired
	private UserRoleService userRoleService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> add(@RequestBody @Valid UserRole role) {
		try {
			return new ResponseEntity<UserRole>(userRoleService.add(role), HttpStatus.CREATED);
		} catch (DuplicateRoleException e) {
			return new ResponseError(e).toResponseEntity(HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(value = "/roles/{id}", method = RequestMethod.PUT)
	public ResponseEntity<UserRole> update(@PathVariable int id, @RequestBody @Valid UserRole role) {
		role.setId(id);
		UserRole result = userRoleService.save(role);
		if (result != null) {
			return new ResponseEntity<UserRole>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<UserRole>(result, HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAll() {
		return ResponseEntity.ok(userRoleService.getAll());
	}

	@RequestMapping(value = "/roles/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> findById(@PathVariable("id") int id) {
		UserRole userRole = userRoleService.findById(id);
		return userRole == null
				? new ResponseError("Role with ID " + id + " does not exist.").toResponseEntity(HttpStatus.NOT_FOUND)
				: ResponseEntity.ok(userRole);
	}
}
