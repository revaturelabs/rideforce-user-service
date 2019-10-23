package com.revature.rideforce.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.rideforce.user.beans.Office;
import com.revature.rideforce.user.services.OfficeService;

@Lazy(true)
@RestController
@RequestMapping("/offices")
@PreAuthorize("hasAnyRole('ROLE_TRAINER','ROLE_ADMIN','ROLE_RIDER', 'ROLE_DRIVER')")
public class OfficeController extends CrudController<Office> {
	@Autowired
	public OfficeController(OfficeService officeService) {
		super(officeService);
	}
}
