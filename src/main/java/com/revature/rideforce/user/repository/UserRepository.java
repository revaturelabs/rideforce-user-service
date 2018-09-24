package com.revature.rideforce.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.revature.rideforce.user.beans.Office;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.beans.UserRole;
@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {
	public User findById(int id);

	public User findByEmail(String email);
	
	public List<User> findByOfficeAndRole(Office office, UserRole role);
}
