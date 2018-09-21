package com.revature.rideshare.user.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.rideshare.user.beans.ContactInfo;
import com.revature.rideshare.user.beans.ResponseError;
import com.revature.rideshare.user.jsonbeans.ContactInfoConverter;
import com.revature.rideshare.user.jsonbeans.JsonContactInfo;
import com.revature.rideshare.user.services.ContactInfoService;

@RestController
public class ContactInfoController {
	@Autowired
	ContactInfoService contactInfoService;

	@Autowired
	ContactInfoConverter contactInfoConverter;

	@RequestMapping(value = "/contact-info/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> findById(@PathVariable("id") int id) {
		ContactInfo contactInfo = contactInfoService.findById(id);
		return contactInfo == null ? new ResponseError("Contact info with ID " + id + " does not exist.")
				.toResponseEntity(HttpStatus.NOT_FOUND) : ResponseEntity.ok(contactInfoConverter.toJson(contactInfo));
	}

	@RequestMapping(value = "/contact-info", method = RequestMethod.POST)
	public ResponseEntity<JsonContactInfo> add(@RequestBody @Valid JsonContactInfo info) {
		info.setId(0);
		return ResponseEntity
				.ok(contactInfoConverter.toJson(contactInfoService.save(contactInfoConverter.fromJson(info))));
	}

	@RequestMapping(value = "/contact-info/{id}", method = RequestMethod.PUT)
	public ResponseEntity<JsonContactInfo> update(@PathVariable int id, @RequestBody @Valid JsonContactInfo info) {
		info.setId(id);
		return ResponseEntity
				.ok(contactInfoConverter.toJson(contactInfoService.save(contactInfoConverter.fromJson(info))));
	}
}
