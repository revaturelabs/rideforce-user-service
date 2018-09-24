package com.revature.rideshare.user.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.rideshare.user.beans.Car;
import com.revature.rideshare.user.beans.User;
import com.revature.rideshare.user.repository.CarsRepository;

@Service
public class CarService implements CrudService<Car> {
	@Autowired
	private CarsRepository carRepository;
	
	@Override
	public List<Car> findAll() {
		return carRepository.findAll();
	}
	
	@Override
	public Car findById(int id) {
		return carRepository.findById(id);
	}
	
	public List<Car> findByOwner(User owner) {
		return carRepository.findByOwner(owner);
	}
	
	@Override
	public Car add(Car car) {
		// Ensure that a new car is created.
		car.setId(0);
		return carRepository.save(car);
	}
	
	@Override
	public Car save(Car car) {
		return carRepository.save(car);
	}
}
