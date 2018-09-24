package com.revature.rideshare.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.revature.rideshare.user.beans.Office;
import com.revature.rideshare.user.services.OfficeService;

@RestController
public class OfficeController extends CrudController<Office> {
	@Autowired
	public OfficeController(OfficeService officeService) {
		super(officeService);
	}
}
