package com.revature.rideshare.user.controllers;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.rideshare.user.beans.ContactType;
import com.revature.rideshare.user.services.ContactTypeService;

@RestController
@RequestMapping("/contact-type")
public class ContactTypeController {
	private ContactTypeService contactTypeService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<ContactType> add(@RequestBody @Valid ContactType type) {
		type.setId(0);
		ContactType result = contactTypeService.save(type);
		if (result != null) {
			return new ResponseEntity<ContactType>(result, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<ContactType>(result, HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping(value="/contact-type/{id}", method = RequestMethod.PUT)
	public ResponseEntity<ContactType> update(@PathVariable int id, @RequestBody @Valid ContactType type) {
		type.setId(id);
		ContactType result = contactTypeService.save(type);
		if (result != null) {
			return new ResponseEntity<ContactType>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<ContactType>(result, HttpStatus.CONFLICT);
		}
	}

}
