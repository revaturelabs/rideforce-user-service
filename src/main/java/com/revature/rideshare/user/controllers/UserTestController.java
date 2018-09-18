package com.revature.rideshare.user.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserTestController {

	@RequestMapping(value="/test", method=RequestMethod.GET)
	public String test() {
		return "user controller works!";
	}
}
