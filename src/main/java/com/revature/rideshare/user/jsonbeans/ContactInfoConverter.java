package com.revature.rideshare.user.jsonbeans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.rideshare.user.beans.ContactInfo;
import com.revature.rideshare.user.beans.ContactType;
import com.revature.rideshare.user.json.UserLinkResolver;
import com.revature.rideshare.user.services.ContactTypeService;

@Service
public class ContactInfoConverter {
	@Autowired
	private UserLinkResolver userLinkResolver;

	@Autowired
	private ContactTypeService contactTypeService;

	public ContactInfo fromJson(JsonContactInfo json) {
		ContactInfo info = new ContactInfo();

		info.setId(json.getId());
		info.setInfo(json.getInfo());

		ContactType type = contactTypeService.findByType(json.getType());
		if (type == null) {
			throw new IllegalArgumentException(json.getType() + " is not a valid contact type.");
		}
		info.setType(type);

		info.setUser(userLinkResolver.resolve(json.getUser()));

		return info;
	}

	public JsonContactInfo toJson(ContactInfo info) {
		JsonContactInfo json = new JsonContactInfo();

		json.setId(info.getId());
		json.setInfo(info.getInfo());
		json.setType(info.getType().getType().toUpperCase());
		json.setUser(info.getUser().toLink());
		
		return json;
	}
}
