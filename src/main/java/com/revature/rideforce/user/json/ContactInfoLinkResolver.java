package com.revature.rideforce.user.json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import com.revature.rideforce.user.beans.ContactInfo;
import com.revature.rideforce.user.repository.ContactInfoRepository;

@Service
public class ContactInfoLinkResolver implements LinkResolver<ContactInfo> {
	private static final AntPathMatcher matcher = new AntPathMatcher();

	@Autowired
	private ContactInfoRepository contactInfoRepository;

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
