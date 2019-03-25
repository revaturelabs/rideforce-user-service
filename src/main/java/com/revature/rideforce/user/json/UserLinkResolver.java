package com.revature.rideforce.user.json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.repository.UserRepository;

/**
 * Service layer class that returns the correct {@link User} object from a given link url. <p>
 * <strong>Members:</strong><br>
 * {@link org.springframework.util.AntPathMatcher AntPathMatcher} matcher - singleton path matcher<br>
 * {@link UserRepository} userRepository
 * @author clpeng
 * @since Iteration1: 10/01/2018
 */
@Service
public class UserLinkResolver implements LinkResolver<User> {
	private static final AntPathMatcher matcher = new AntPathMatcher();

	@Autowired
	private UserRepository userRepository;

	/* (non-Javadoc)
	 * @see com.revature.rideforce.user.json.LinkResolver#resolve(java.lang.String)
	 */
	@Override
	public User resolve(String link) {
		try {
			int id = Integer.parseInt(matcher.extractUriTemplateVariables("/users/{id}", link).get("id"));
			return userRepository.findById(id);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(link + " is not a valid user link.");
		}
	}
}
