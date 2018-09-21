package com.revature.rideshare.user.jsonbeans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.util.UriComponentsBuilder;

import com.revature.rideshare.user.beans.Car;
import com.revature.rideshare.user.services.UserService;

@Service
public class CarConverter {
	@Autowired
	UserService userService;

	public Car fromJson(JsonCar json) {
		Car car = new Car();

		car.setId(json.getId());
		car.setMake(json.getMake());
		car.setModel(json.getModel());
		car.setYear(json.getYear());

		AntPathMatcher matcher = new AntPathMatcher();
		int ownerId = Integer.parseInt(matcher.extractUriTemplateVariables("/users/{id}", json.getOwner()).get("id"));
		car.setOwner(userService.findById(ownerId));

		return car;
	}

	public JsonCar toJson(Car car) {
		JsonCar json = new JsonCar();

		json.setId(car.getId());
		json.setMake(car.getMake());
		json.setModel(car.getModel());
		json.setYear(car.getYear());

		json.setOwner(UriComponentsBuilder.fromPath("/users/{id}").buildAndExpand(car.getOwner().getId()).toString());
		
		return json;
	}
}
