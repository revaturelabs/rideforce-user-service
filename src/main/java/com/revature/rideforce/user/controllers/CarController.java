package com.revature.rideforce.user.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.rideforce.user.beans.Car;
import com.revature.rideforce.user.services.CarService;

/**
 * 
 * responsible for intercepting the http request methods at the /cars endpoint
 * 
 * @author clpeng
 */
@CrossOrigin(origins="*")
@RestController
@Lazy(true)
@RequestMapping("/cars")
@PreAuthorize("hasAnyRole('ROLE_TRAINER','ROLE_ADMIN','ROLE_RIDER', 'ROLE_DRIVER')")
public class CarController extends CrudController<Car> {
	private static final Logger log = LoggerFactory.getLogger(CarController.class);
	
	@Autowired
	public CarController(CarService carService) {
		super(carService);
		log.debug("CarController object created with CarService");
		log.info("/cars endpoint reached");
	}
}
