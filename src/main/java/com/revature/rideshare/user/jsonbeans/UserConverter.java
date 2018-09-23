package com.revature.rideshare.user.jsonbeans;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.rideshare.user.beans.Car;
import com.revature.rideshare.user.beans.ContactInfo;
import com.revature.rideshare.user.beans.User;
import com.revature.rideshare.user.json.CarLinkResolver;
import com.revature.rideshare.user.json.ContactInfoLinkResolver;
import com.revature.rideshare.user.json.OfficeLinkResolver;
import com.revature.rideshare.user.json.UserRoleResolver;

@Service
public class UserConverter {
	@Autowired
	private UserRoleResolver userRoleResolver;

	@Autowired
	private OfficeLinkResolver officeLinkResolver;

	@Autowired
	private CarLinkResolver carLinkResolver;

	@Autowired
	private ContactInfoLinkResolver contactInfoLinkResolver;

	public User fromJson(JsonUser json) {
		User user = new User();

		user.setId(json.getId());
		user.setFirstName(json.getFirstName());
		user.setLastName(json.getLastName());
		user.setEmail(json.getEmail());
		user.setAddress(json.getAddress());
		user.setPassword(json.getPassword());
		user.setPhotoUrl(json.getPhotoUrl());
		user.setActive(json.isActive());
		user.setBatchEnd(json.getBatchEnd());
		user.setVenmo(json.getVenmo());
		user.setRole(userRoleResolver.resolve(json.getRole()));
		user.setOffice(officeLinkResolver.resolve(json.getOffice()));
		user.setCars(json.getCars().stream().map(carLinkResolver::resolve).collect(Collectors.toSet()));
		user.setContactInfo(
				json.getContactInfo().stream().map(contactInfoLinkResolver::resolve).collect(Collectors.toSet()));

		return user;
	}

	public JsonUser toJson(User user) {
		JsonUser json = new JsonUser();

		json.setId(user.getId());
		json.setFirstName(user.getFirstName());
		json.setLastName(user.getLastName());
		json.setEmail(user.getEmail());
		json.setAddress(user.getAddress());
		json.setPassword(user.getPassword());
		json.setPhotoUrl(user.getPhotoUrl());
		json.setActive(user.isActive());
		json.setBatchEnd(user.getBatchEnd());
		json.setVenmo(user.getVenmo());
		json.setRole(user.getRole().toEnumString());
		json.setOffice(user.getOffice().toLink());
		json.setCars(user.getCars().stream().map(Car::toLink).collect(Collectors.toSet()));
		json.setContactInfo(user.getContactInfo().stream().map(ContactInfo::toLink).collect(Collectors.toSet()));

		return json;
	}
}
