package com.revature.rideshare.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.rideshare.services.ContactInfoService;
import com.revature.rideshare.user.beans.Car;
import com.revature.rideshare.user.beans.ContactInfo;

@RestController
public class ContactInfoController {
	
	@Autowired
	ContactInfoService contactInfoService;
	
	@RequestMapping(value="/contact-info/{id}", method=RequestMethod.GET)
	public ResponseEntity<ContactInfo> getContactInfo(@PathVariable int id)
	{
		ContactInfo result = contactInfoService.getContactInfoById(id);
		if (result != null)
		{
			return new ResponseEntity<ContactInfo>(result, HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<ContactInfo>(result, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value="/contact-info", method=RequestMethod.POST)
	public ResponseEntity<ContactInfo> addContactInfo(@RequestBody ContactInfo info)
	{
		ContactInfo result = contactInfoService.addContactInfo(info);
		if (result != null)
		{
			return new ResponseEntity<ContactInfo>(result, HttpStatus.CREATED);
		}
		else
		{
			return new ResponseEntity<ContactInfo>(result, HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping(value="/contact-info/{id}", method=RequestMethod.PUT)
	public ResponseEntity<ContactInfo> updateContactInfo(@PathVariable int id, @RequestBody ContactInfo info)
	{
		ContactInfo result = contactInfoService.updateContactInfo(id, info);
		if (result != null)
		{
			return new ResponseEntity<ContactInfo>(result, HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<ContactInfo>(result, HttpStatus.CONFLICT);
		}
	}
}
