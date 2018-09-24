package com.revature.rideforce.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.rideforce.user.beans.ContactInfo;
import com.revature.rideforce.user.services.ContactInfoService;

@RestController
@RequestMapping("/contact-info")
public class ContactInfoController extends CrudController<ContactInfo> {
	@Autowired
	public ContactInfoController(ContactInfoService contactInfoService) {
		super(contactInfoService);
	}
}
