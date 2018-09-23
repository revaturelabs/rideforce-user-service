package com.revature.rideshare.user.json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;

import com.revature.rideshare.user.beans.ContactInfo;
import com.revature.rideshare.user.services.ContactInfoService;

public class ContactInfoLinkResolver implements LinkResolver<ContactInfo> {
	private static final AntPathMatcher matcher = new AntPathMatcher();

	@Autowired
	private ContactInfoService contactInfoService;

	@Override
	public ContactInfo resolve(String link) {
		try {
			int id = Integer.parseInt(matcher.extractUriTemplateVariables("/contact-info/{id}", link).get("id"));
			return contactInfoService.findById(id);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(link + " is not a valid contact info link.");
		}
	}
}
