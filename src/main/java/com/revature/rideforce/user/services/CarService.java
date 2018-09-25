package com.revature.rideforce.user.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.rideforce.user.beans.Car;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.exceptions.PermissionDeniedException;
import com.revature.rideforce.user.repository.CarRepository;

@Service
public class CarService extends CrudService<Car> {
	private CarRepository carRepository;

	@Autowired
	public CarService(CarRepository carRepository) {
		super(carRepository);
		this.carRepository = carRepository;
	}

	public List<Car> findByOwner(User owner) throws PermissionDeniedException {
		User current = authenticationService.getCurrentUser();
		if (current == null || (!current.isAdmin() && !current.isTrainer() && current.getId() != owner.getId())) {
			throw new PermissionDeniedException("Permission denied to access this user's cars.");
		}
		return carRepository.findByOwner(owner);
	}
}
