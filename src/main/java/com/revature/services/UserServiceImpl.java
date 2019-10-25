package com.revature.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.models.User;
import com.revature.repos.UserRepo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepo ur;

	@Override
	public User getUserById(int id) {
		return ur.findById(id).get();
	}

	@Override
	public User getUserByEmail(String email) {
		return ur.findByEmail(email);
	}

	@Override
	public List<User> getAllUsers() {
		return (List<User>)ur.findAll();
	}

	@Override
	public User createUser(User user) {
		return ur.save(user);
	}

	@Override
	public User updateUser(User user) {
		return ur.save(user);
	}

	@Override
	public boolean deleteUser(User user) {
		try {
			ur.delete(user);
		} catch (IllegalArgumentException e) {
			return false;
		}
		
		return true;
	}
	
}
