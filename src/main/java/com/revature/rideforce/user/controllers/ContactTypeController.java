package com.revature.rideforce.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.rideforce.user.beans.ContactType;
import com.revature.rideforce.user.services.ContactTypeService;

@RestController
@Lazy(true)
@RequestMapping("/contact-types")
@PreAuthorize("hasAnyRole('ROLE_TRAINER','ROLE_ADMIN','ROLE_RIDER', 'ROLE_DRIVER')")
public class ContactTypeController extends CrudController<ContactType> {
	@Autowired
	public ContactTypeController(ContactTypeService contactTypeService) {
		super(contactTypeService);
	}
}
