package com.revature.rideforce.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.rideforce.user.beans.Car;
import com.revature.rideforce.user.services.CarService;


import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * responsible for intercepting the http request methods at the /cars endpoint
 * 
 * @author clpeng
 */

@RestController
@Lazy(true)
@RequestMapping("/cars")
@PreAuthorize("hasAnyRole('ROLE_TRAINER','ROLE_ADMIN','ROLE_RIDER', 'ROLE_DRIVER')")
public class CarController extends CrudController<Car> {
  static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	@Autowired
	public CarController(CarService carService) {
		super(carService);
		log.debug("CarController object created with CarService");
		log.info("/cars endpoint reached");
	}
}
