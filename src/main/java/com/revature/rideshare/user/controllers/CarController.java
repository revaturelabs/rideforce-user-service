package com.revature.rideshare.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.rideshare.services.CarService;
import com.revature.rideshare.user.beans.Car;

@RestController
public class CarController {
	
	@Autowired
	CarService carService;
	
	@RequestMapping(value="/cars/{id}", method=RequestMethod.GET)
	public ResponseEntity<Car> getCar(@PathVariable int id)
	{
		Car result = carService.getCarById(id);
		if (result != null)
		{
			return new ResponseEntity<Car>(result, HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<Car>(result, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value="/cars", method=RequestMethod.POST)
	public ResponseEntity<Car> addCar(@RequestBody Car car)
	{
		Car result = carService.addCar(car);
		if (result != null)
		{
			return new ResponseEntity<Car>(result, HttpStatus.CREATED);
		}
		else
		{
			return new ResponseEntity<Car>(result, HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping(value="/cars/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Car> updateCar(@PathVariable int id, @RequestBody Car car)
	{
		Car result = carService.updateCar(id, car);
		if (result != null)
		{
			return new ResponseEntity<Car>(result, HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<Car>(result, HttpStatus.CONFLICT);
		}
	}
}
