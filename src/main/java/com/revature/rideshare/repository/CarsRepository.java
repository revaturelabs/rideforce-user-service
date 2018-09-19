package com.revature.rideshare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.rideshare.user.beans.Car;
import com.revature.rideshare.user.beans.User;

public interface CarsRepository extends JpaRepository<Car, Integer> {
	public Car findByOwner(int id);
}
