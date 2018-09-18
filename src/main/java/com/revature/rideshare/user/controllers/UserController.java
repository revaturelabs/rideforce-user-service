package com.revature.rideshare.user.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class UserController {
	@RequestMapping(value="/user", method=RequestMethod.GET)
	public String test() {
		return "user controller works!";
	}
}
