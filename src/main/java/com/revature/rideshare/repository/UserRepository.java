package com.revature.rideshare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.rideshare.user.beans.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	public User findByUsername(String username);
}
