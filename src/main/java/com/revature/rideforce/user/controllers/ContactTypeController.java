package com.revature.rideforce.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.rideforce.user.beans.ContactType;
import com.revature.rideforce.user.services.ContactTypeService;

@RestController
@RequestMapping("/contact-types")
public class ContactTypeController extends CrudController<ContactType> {
	@Autowired
	public ContactTypeController(ContactTypeService contactTypeService) {
		super(contactTypeService);
	}
}
