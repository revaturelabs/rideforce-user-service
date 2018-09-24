package com.revature.rideshare.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.rideshare.user.beans.ContactInfo;
import com.revature.rideshare.user.services.ContactInfoService;

@RestController
@RequestMapping("/contact-info")
public class ContactInfoController extends CrudController<ContactInfo> {
	@Autowired
	public ContactInfoController(ContactInfoService contactInfoService) {
		super(contactInfoService);
	}
}
