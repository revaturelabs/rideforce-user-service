package com.revature.rideshare.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.rideshare.services.OfficeService;
import com.revature.rideshare.user.beans.Office;
import com.revature.rideshare.user.beans.ResponseError;

@RestController
public class OfficeController {
	@Autowired
	OfficeService officeService;

	@RequestMapping(value = "/offices/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> findById(@PathVariable("id") int id) {
		Office office = officeService.findById(id);
		return office == null
				? new ResponseError("Office with ID " + id + " does not exist.").toResponseEntity(HttpStatus.NOT_FOUND)
				: ResponseEntity.ok(office);
	}

	@RequestMapping(value = "/offices", method = RequestMethod.POST)
	public ResponseEntity<Office> add(@RequestBody Office office) {
		office.setId(0);
		Office result = officeService.save(office);
		if (result != null) {
			return new ResponseEntity<Office>(result, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Office>(result, HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(value = "/offices/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Office> update(@PathVariable int id, @RequestBody Office office) {
		office.setId(id);
		Office result = officeService.save(office);
		if (result != null) {
			return new ResponseEntity<Office>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<Office>(result, HttpStatus.CONFLICT);
		}
	}
}
