package com.revature.rideshare.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.rideshare.user.beans.UserRole;
import com.revature.rideshare.user.services.UserRoleService;

@RestController
@RequestMapping("/roles")
public class UserRoleController extends CrudController<UserRole> {
	@Autowired
	public UserRoleController(UserRoleService userRoleService) {
		super(userRoleService);
	}
}
