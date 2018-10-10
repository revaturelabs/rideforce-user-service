package com.revature.rideforce.user.json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.rideforce.user.beans.ContactType;
import com.revature.rideforce.user.services.ContactTypeService;

/**
 * Service layer class that returns the correct {@link com.revature.rideforce.user.ContactType ContactType} object based on what String type
 * was provided by a user.<br>If the String type given by the user is invalid, throws exceptions. <p>
 * <strong>Members:</strong><br>
 * {@link com.revature.rideforce.user.repository.ContactTypeService ContactTypeService} ContactTypeService
 * @author clpeng
 * @since Iteration1: 10/01/2018
 */
@Service
public class ContactTypeResolver implements EnumLikeResolver<ContactType> {
	@Autowired
	private ContactTypeService contactTypeService;

	/* (non-Javadoc)
	 * @see com.revature.rideforce.user.json.EnumLikeResolver#resolve(java.lang.String)
	 */
	/**
	 * @throws IllegalArgumentException - for alerting the user the arguments passed in from the link are illegal
	 */
	@Override
	public ContactType resolve(String value) {
		ContactType type = contactTypeService.findByType(value);
		if (type == null) {
			throw new IllegalArgumentException(value + " is not a valid contact type.");
		}
		return type;
	}
}
