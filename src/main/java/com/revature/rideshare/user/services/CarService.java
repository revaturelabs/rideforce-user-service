package com.revature.rideshare.user.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.rideshare.user.beans.Car;
import com.revature.rideshare.user.beans.User;
import com.revature.rideshare.user.repository.CarRepository;

@Service
public class CarService extends CrudService<Car> {
	private CarRepository carRepository;

	@Autowired
	public CarService(CarRepository carRepository) {
		super(carRepository);
		this.carRepository = carRepository;
	}

	public List<Car> findByOwner(User owner) {
		return carRepository.findByOwner(owner);
	}
}
