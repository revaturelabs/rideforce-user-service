package com.revature.rideshare.user.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.rideshare.user.beans.Car;
import com.revature.rideshare.user.beans.ResponseError;
import com.revature.rideshare.user.jsonbeans.CarConverter;
import com.revature.rideshare.user.jsonbeans.JsonCar;
import com.revature.rideshare.user.services.CarService;

@RestController
public class CarController {
	@Autowired
	CarService carService;

	@Autowired
	CarConverter carConverter;
	
	@RequestMapping(value="/cars", method = RequestMethod.GET)
	public ResponseEntity<List<JsonCar>> findAll() {
		List<Car> users = carService.findAll();
		return ResponseEntity.ok(users.stream().map(carConverter::toJson).collect(Collectors.toList()));
	}

	@RequestMapping(value = "/cars/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> findById(@PathVariable("id") int id) {
		Car car = carService.findById(id);
		return car == null
				? new ResponseError("Car with ID " + id + " does not exist.").toResponseEntity(HttpStatus.NOT_FOUND)
				: ResponseEntity.ok(carConverter.toJson(car));
	}

	@RequestMapping(value = "/cars", method = RequestMethod.POST)
	public ResponseEntity<JsonCar> add(@RequestBody @Valid JsonCar car) {
		car.setId(0);
		return ResponseEntity.ok(carConverter.toJson(carService.save(carConverter.fromJson(car))));
	}

	@RequestMapping(value = "/cars/{id}", method = RequestMethod.PUT)
	public ResponseEntity<JsonCar> update(@PathVariable("id") int id, @RequestBody @Valid JsonCar car) {
		car.setId(id);
		return ResponseEntity.ok(carConverter.toJson(carService.save(carConverter.fromJson(car))));
	}
}
