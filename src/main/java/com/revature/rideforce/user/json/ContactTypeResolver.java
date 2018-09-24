package com.revature.rideforce.user.json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.rideforce.user.beans.ContactType;
import com.revature.rideforce.user.services.ContactTypeService;

@Service
public class ContactTypeResolver implements EnumLikeResolver<ContactType> {
	@Autowired
	private ContactTypeService contactTypeService;

	@Override
	public ContactType resolve(String value) {
		ContactType type = contactTypeService.findByType(value);
		if (type == null) {
			throw new IllegalArgumentException(value + " is not a valid contact type.");
		}
		return type;
	}
}
