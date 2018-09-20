package com.revature.rideshare.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.rideshare.user.beans.Office;

@RestController
public class OfficeController {

	@Autowired
	OfficeService officeService;
	
	@RequestMapping(value="/offices/{id}", method=RequestMethod.GET)
	public ResponseEntity<Office> getOffice(@PathVariable int id)
	{
		Office result = officeService.getOfficeById(id);
		if (result != null)
		{
			return new ResponseEntity<Office>(result, HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<Office>(result, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value="/offices", method=RequestMethod.POST)
	public ResponseEntity<Office> addOffice(@RequestBody Office office)
	{
		Office result = officeService.addOffice(office);
		if (result != null)
		{
			return new ResponseEntity<Office>(result, HttpStatus.CREATED);
		}
		else
		{
			return new ResponseEntity<Office>(result, HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping(value="/offices/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Office> updateOffice(@PathVariable int id, @RequestBody Office office)
	{
		Office result = officeService.updateOffice(id, office);
		if (result != null)
		{
			return new ResponseEntity<Office>(result, HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<Office>(result, HttpStatus.CONFLICT);
		}
	}
}
