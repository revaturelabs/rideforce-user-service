package com.revature.rideshare.user.jsonbeans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.rideshare.user.beans.Car;
import com.revature.rideshare.user.json.UserLinkResolver;

@Service
public class CarConverter {
	@Autowired
	UserLinkResolver userLinkResolver;

	public Car fromJson(JsonCar json) {
		Car car = new Car();

		car.setId(json.getId());
		car.setMake(json.getMake());
		car.setModel(json.getModel());
		car.setYear(json.getYear());
		car.setOwner(userLinkResolver.resolve(json.getOwner()));

		return car;
	}

	public JsonCar toJson(Car car) {
		JsonCar json = new JsonCar();

		json.setId(car.getId());
		json.setMake(car.getMake());
		json.setModel(car.getModel());
		json.setYear(car.getYear());
		json.setOwner(car.getOwner().toLink());
		
		return json;
	}
}
