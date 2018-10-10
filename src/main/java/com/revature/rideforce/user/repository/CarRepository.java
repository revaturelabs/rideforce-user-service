package com.revature.rideforce.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.rideforce.user.beans.Car;
import com.revature.rideforce.user.beans.User;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
	public Car findById(int id);
	
	/**
	 * List the cars a specific user owns
	 * @param owner User specified
	 * @return list of cars belonging to the owner specified
	 */
	public List<Car> findByOwner(User owner);
}
