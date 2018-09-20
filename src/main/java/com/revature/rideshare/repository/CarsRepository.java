package com.revature.rideshare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.rideshare.user.beans.Car;
import com.revature.rideshare.user.beans.User;

@Repository
public interface CarsRepository extends JpaRepository<Car, Integer> {
	public Car findById(int id);

	public List<Car> findByOwner(User owner);
}
