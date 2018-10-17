package com.revature.rideforce.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.rideforce.user.beans.UserRole;
import com.revature.rideforce.user.services.UserRoleService;

@RestController
@Lazy(true)
@RequestMapping("/roles")
@PreAuthorize("hasAnyRole('ROLE_TRAINER','ROLE_ADMIN','ROLE_RIDER', 'ROLE_DRIVER')")
public class UserRoleController extends CrudController<UserRole> {
	@Autowired
	public UserRoleController(UserRoleService userRoleService) {
		super(userRoleService);
	}
}
