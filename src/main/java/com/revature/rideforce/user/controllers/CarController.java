package com.revature.rideforce.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.rideforce.user.beans.Car;
import com.revature.rideforce.user.services.CarService;

/**
 * 
 * responsible for intercepting the http request methods at the /cars endpoint
 * @author clpeng
 */
@RestController
@RequestMapping("/cars")
public class CarController extends CrudController<Car> {
	@Autowired
	public CarController(CarService carService) {
		super(carService);
    logger.debug("CarController object created with CarService");
    logger.info("/cars endpoint reached");
	}
}
