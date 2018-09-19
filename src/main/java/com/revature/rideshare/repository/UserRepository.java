package com.revature.rideshare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.revature.rideshare.user.beans.User;
@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {
	
	
	public User findByUsername(String username);
	
	public User addOne(User user);
	
}
