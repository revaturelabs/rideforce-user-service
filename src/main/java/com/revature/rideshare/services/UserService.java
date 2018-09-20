package com.revature.rideshare.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.rideshare.repository.UserRepository;
import com.revature.rideshare.user.beans.User;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	public User findById(int id) {
		return userRepository.getOne(id);
	}
	
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public User save(User user) {
		return userRepository.save(user);
	}
}
