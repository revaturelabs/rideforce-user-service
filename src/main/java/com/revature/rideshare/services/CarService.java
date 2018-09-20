package com.revature.rideshare.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.rideshare.repository.CarsRepository;
import com.revature.rideshare.user.beans.Car;
import com.revature.rideshare.user.beans.User;

@Service
public class CarService {
	@Autowired
	private CarsRepository carRepository;
	
	public List<Car> findAll() {
		return carRepository.findAll();
	}
	
	public Car findById(int id) {
		return carRepository.findById(id);
	}
	
	public List<Car> findByOwner(User owner) {
		return carRepository.findByOwner(owner);
	}
	
	public Car save(Car car) {
		return carRepository.save(car);
	}
}
