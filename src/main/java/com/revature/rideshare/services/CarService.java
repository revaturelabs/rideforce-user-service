package com.revature.rideshare.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.revature.rideshare.repository.CarsRepository;
import com.revature.rideshare.user.beans.Car;
import com.revature.rideshare.user.beans.User;

public class CarService {
	
	@Autowired
	private CarsRepository carRepository;
	
	public Car getOne(User user) {
	return carRepository.findByOwner(user.getId());
	}
	
	public Car save(Car car) {
		return carRepository.save(car);
	}
	
	public void delete(Car car) {
		carRepository.delete(car);
	}
	
	public List<Car> findAll() {
		return carRepository.findAll();
	}
}
