package com.revature.rideforce.user.json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.services.UserService;

@Service
public class UserLinkResolver implements LinkResolver<User> {
	private static final AntPathMatcher matcher = new AntPathMatcher();

	@Autowired
	private UserService userService;

	@Override
	public User resolve(String link) {
		try {
			int id = Integer.parseInt(matcher.extractUriTemplateVariables("/users/{id}", link).get("id"));
			return userService.findById(id);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(link + " is not a valid user link.");
		}
	}
}
