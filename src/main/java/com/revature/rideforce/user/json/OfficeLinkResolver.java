package com.revature.rideforce.user.json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import com.revature.rideforce.user.beans.Office;
import com.revature.rideforce.user.services.OfficeService;

@Service
public class OfficeLinkResolver implements LinkResolver<Office> {
	private static final AntPathMatcher matcher = new AntPathMatcher();
	
	@Autowired
	private OfficeService officeService;

	@Override
	public Office resolve(String link) {
		try {
			int id = Integer.parseInt(matcher.extractUriTemplateVariables("/offices/{id}", link).get("id"));
			return officeService.findById(id);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(link + " is not a valid office link.");
		}
	}
}
