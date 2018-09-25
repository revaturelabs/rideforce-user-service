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
		// Make this more restrictive if necessary.
		if (!canFindAll()) {
			throw new PermissionDeniedException("Permission denied to access user's cars.");
		}
		return carRepository.findByOwner(owner);
	}

	@Override
	protected boolean canAdd(User user, Car obj) {
		// Users can only add cars for themselves, except admins who can add
		// cars to any user.
		return user != null && (user.isAdmin() || user.getId() == obj.getOwner().getId());
	}

	@Override
	protected boolean canSave(User user, Car obj) {
		// Users can only save cars for themselves, except admins who can save
		// cars for any user.
		return user != null && (user.isAdmin() || user.getId() == obj.getOwner().getId());
	}
}
