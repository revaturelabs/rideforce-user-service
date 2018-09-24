package com.revature.rideshare.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.rideshare.user.beans.Car;
import com.revature.rideshare.user.beans.User;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
	public Car findById(int id);

	public List<Car> findByOwner(User owner);
}
