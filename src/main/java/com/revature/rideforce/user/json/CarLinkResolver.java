package com.revature.rideforce.user.json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import com.revature.rideforce.user.beans.Car;
import com.revature.rideforce.user.services.CarService;

@Service
public class CarLinkResolver implements LinkResolver<Car> {
	private static final AntPathMatcher matcher = new AntPathMatcher();

	@Autowired
	private CarService carService;

	@Override
	public Car resolve(String link) {
		try {
			int id = Integer.parseInt(matcher.extractUriTemplateVariables("/cars/{id}", link).get("id"));
			return carService.findById(id);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(link + " is not a valid car link.");
		}
	}
}
