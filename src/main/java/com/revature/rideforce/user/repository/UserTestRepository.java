package com.revature.rideforce.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.rideforce.user.beans.User;

@Repository
public interface UserTestRepository extends JpaRepository<User, Integer>{
	
	public User findByEmail(String email);
	
	public User findById(String id);
}
