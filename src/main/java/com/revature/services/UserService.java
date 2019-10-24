package com.revature.services;

import java.util.List;

import com.revature.models.User;

public interface UserService {

	public User getUserById(int id);
	public User getUserByEmail(String email);
	public List<User> getAllUsers();
	public User createUser(User user);
	public User updateUser(User user);
	public boolean deleteUser(User user);
	
}
