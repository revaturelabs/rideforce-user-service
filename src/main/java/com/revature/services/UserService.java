package com.revature.services;

import java.util.List;

import com.revature.models.User;

public interface UserService {

	public User getUserById(int id);
	public User getUserByEmail(String email);
	public User userLogin(String email, String password);
	public List<User> getAllUsers();
	public List<User> getAllDrivers();
	public List<User> getAllActiveDrivers();
	public List<User> getAllInactiveDrivers();
	public User createUser(User user);
	public User updateUser(User user);
	public boolean deleteUser(User user);
	
}
