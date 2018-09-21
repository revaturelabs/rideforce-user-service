package com.revature.rideshare.user.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.rideshare.user.beans.UserRole;
import com.revature.rideshare.user.services.UserRoleService;

@RestController
@RequestMapping("/roles")
public class UserRoleController {
	@Autowired
	private UserRoleService userRoleService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> add(@RequestBody @Valid UserRole role) {
		return ResponseEntity.ok(userRoleService.save(role));
	}
}
