package com.revature.rideshare.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.revature.rideshare.repository.UserRepository;
import com.revature.rideshare.user.beans.User;

public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	
	public User findById( int id) {
		return userRepository.findOne(id);
	}
	
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	
	
}
