package com.revature.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.models.Role;
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
	public User userLogin(String email, String password) {
		return ur.findByEmailAndPassword(email, password);
	}

	@Override
	public List<User> getAllUsers() {
		return (List<User>)ur.findAll();
	}
	
	@Override
	public List<User> getAllDrivers() {
		Role r = new Role(1, "Driver");
		ArrayList<Role> rList = new ArrayList<Role>();
		rList.add(r);
		return ur.findByRolesIn(rList);
	}

	@Override
	public List<User> getAllActiveDrivers() {
		List<User> drivers = getAllDrivers();
		ArrayList<User> actives = new ArrayList<User>();
		for(User u : drivers) {
			if(u.getIsActive() == true) {
				actives.add(u);
			}
		}
		return actives;
	}

	@Override
	public List<User> getAllInactiveDrivers() {
		List<User> drivers = getAllDrivers();
		ArrayList<User> inactives = new ArrayList<User>();
		for(User u : drivers) {
			if(u.getIsActive() == false) {
				inactives.add(u);
			}
		}
		return inactives;
	}

	@Override
	public User createUser(User user) {
		ur.save(user);
		return user;
	}

	@Override
	public User updateUser(User user) {
		ur.save(user);
		return user;
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
