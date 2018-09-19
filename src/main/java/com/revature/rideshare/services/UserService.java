package com.revature.rideshare.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.rideshare.repository.UserRepository;
import com.revature.rideshare.user.beans.User;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	
	public User findById( int id) {
		return userRepository.getOne(id);
	}
	
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	
	
}
