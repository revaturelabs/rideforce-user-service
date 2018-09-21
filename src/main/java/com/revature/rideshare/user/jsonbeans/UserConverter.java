package com.revature.rideshare.user.jsonbeans;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.util.UriComponentsBuilder;

import com.revature.rideshare.user.beans.Office;
import com.revature.rideshare.user.beans.User;
import com.revature.rideshare.user.beans.UserRole;
import com.revature.rideshare.user.services.CarService;
import com.revature.rideshare.user.services.ContactInfoService;
import com.revature.rideshare.user.services.OfficeService;
import com.revature.rideshare.user.services.UserRoleService;

@Service
public class UserConverter {
	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private OfficeService officeService;

	@Autowired
	private CarService carService;

	@Autowired
	private ContactInfoService contactInfoService;

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

		UserRole role = userRoleService.findByType(json.getRole());
		if (role == null) {
			throw new IllegalArgumentException(json.getRole() + " is not a valid role.");
		}
		user.setRole(role);

		AntPathMatcher matcher = new AntPathMatcher();
		int officeId = Integer
				.parseInt(matcher.extractUriTemplateVariables("/offices/{id}", json.getOffice()).get("id"));
		Office office = officeService.findById(officeId);
		user.setOffice(office);

		user.setCars(json.getCars().stream().map(uri -> {
			int id = Integer.parseInt(matcher.extractUriTemplateVariables("/cars/{id}", uri).get("id"));
			return carService.findById(id);
		}).collect(Collectors.toSet()));

		user.setContactInfo(json.getContactInfo().stream().map(uri -> {
			int id = Integer.parseInt(matcher.extractUriTemplateVariables("/contact-info/{id}", uri).get("id"));
			return contactInfoService.findById(id);
		}).collect(Collectors.toSet()));

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

		json.setRole(user.getRole().getType().toUpperCase());
		json.setOffice(
				UriComponentsBuilder.fromPath("/offices/{id}").buildAndExpand(user.getOffice().getId()).toString());
		json.setCars(user.getCars().stream()
				.map(car -> UriComponentsBuilder.fromPath("/cars/{id}").buildAndExpand(car.getId()).toString())
				.collect(Collectors.toSet()));
		json.setContactInfo(user.getContactInfo().stream().map(
				info -> UriComponentsBuilder.fromPath("/contact-info/{id}").buildAndExpand(info.getId()).toString())
				.collect(Collectors.toSet()));
		
		return json;
	}
}
