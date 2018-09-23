package com.revature.rideshare.user.jsonbeans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.rideshare.user.beans.ContactInfo;
import com.revature.rideshare.user.json.ContactTypeResolver;
import com.revature.rideshare.user.json.UserLinkResolver;

@Service
public class ContactInfoConverter {
	@Autowired
	private UserLinkResolver userLinkResolver;

	@Autowired
	private ContactTypeResolver contactTypeResolver;

	public ContactInfo fromJson(JsonContactInfo json) {
		ContactInfo info = new ContactInfo();

		info.setId(json.getId());
		info.setInfo(json.getInfo());
		info.setType(contactTypeResolver.resolve(json.getType()));
		info.setUser(userLinkResolver.resolve(json.getUser()));

		return info;
	}

	public JsonContactInfo toJson(ContactInfo info) {
		JsonContactInfo json = new JsonContactInfo();

		json.setId(info.getId());
		json.setInfo(info.getInfo());
		json.setType(info.getType().toEnumString());
		json.setUser(info.getUser().toLink());
		
		return json;
	}
}
