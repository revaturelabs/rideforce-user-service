package com.revature.rideforce.user.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.rideforce.user.beans.Car;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.exceptions.PermissionDeniedException;
import com.revature.rideforce.user.repository.CarRepository;

/**
  Service to check permissions of a user's car management
  */
@Service
public class CarService extends CrudService<Car> {
	private CarRepository carRepository;

	@Autowired
	public CarService(CarRepository carRepository) {
		super(carRepository);
		this.carRepository = carRepository;
	}
	
	/**
	 * get the list of cars a specific user owns
	 * @param owner the {@linkplain User} to be searched up
	 * @return the List of Car objects belonging to a user
	 * @throws PermissionDeniedException
	 */
	public List<Car> findByOwner(User owner) throws PermissionDeniedException {
		// Make this more restrictive if necessary.
		if (!canFindAll()) {
			throw new PermissionDeniedException("Permission denied to access user's cars.");
		}
		return carRepository.findByOwner(owner);
	}

  /**
    Checks if user can add a car 
    @param User the owner that is trying to access the cars
    @param Car the car object whose recorded user must be checked against the user trying to access
    @return true if user is admin or owner of car
    */
	@Override
	protected boolean canAdd(User user, Car obj) {
		// Users can only add cars for themselves, except admins who can add
		// cars to any user.
		return user != null && (user.isAdmin() || user.getId() == obj.getOwner().getId());
	}
  /**
    Checks if user can save a car 
    @param User the owner that is trying to access the cars
    @param Car the car object whose recorded user must be checked against the user trying to access
    @return true if user is admin or owner
    */
	@Override
	protected boolean canSave(User user, Car obj) {
		// Users can only save cars for themselves, except admins who can save
		// cars for any user.
		return user != null && (user.isAdmin() || user.getId() == obj.getOwner().getId());
	}
}
