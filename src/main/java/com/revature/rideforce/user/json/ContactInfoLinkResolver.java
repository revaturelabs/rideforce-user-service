package com.revature.rideforce.user.json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import com.revature.rideforce.user.beans.ContactInfo;
import com.revature.rideforce.user.repository.ContactInfoRepository;

/**
 * Service layer class that returns the correct {@link com.revature.rideforce.user.ContactInfo ContactInfo} object from a given link url. <p>
 * <strong>Members:</strong><br>
 * {@link org.springframework.util.AntPathMatcher AntPathMatcher} matcher - singleton path matcher<br>
 * {@link com.revature.rideforce.user.repository.ContactInfoRepository ContactInfoRepository} ContactInfoRepository
 * @author clpeng
 * @since Iteration1: 10/01/2018
 */
@Service
public class ContactInfoLinkResolver implements LinkResolver<ContactInfo> {
	private static final AntPathMatcher matcher = new AntPathMatcher();

	@Autowired
	private ContactInfoRepository contactInfoRepository;

	/* (non-Javadoc)
	 * @see com.revature.rideforce.user.json.LinkResolver#resolve(java.lang.String)
	 */
	/**
	 * @throws NumberFormatException - for using Integer.parseInt() on a path variable that isn't a number
	 * @throws IllegalArgumentException - for alerting the user the arguments passed in from the link are illegal
	 */
	@Override
	public ContactInfo resolve(String link) {
		try {
			int id = Integer.parseInt(matcher.extractUriTemplateVariables("/contact-info/{id}", link).get("id"));
			return contactInfoRepository.findById(id);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(link + " is not a valid contact info link.");
		}
	}
}
