package com.revature.rideforce.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.rideforce.user.beans.ContactInfo;
import com.revature.rideforce.user.services.ContactInfoService;

@RestController
@Lazy(true)
@RequestMapping("/contact-info")
@PreAuthorize("hasAnyRole('ROLE_TRAINER','ROLE_ADMIN','ROLE_RIDER', 'ROLE_DRIVER')")
public class ContactInfoController extends CrudController<ContactInfo> {
	@Autowired
	public ContactInfoController(ContactInfoService contactInfoService) {
		super(contactInfoService);
	}
}
