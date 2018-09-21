package com.revature.rideshare.user.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.rideshare.user.beans.UserRole;
import com.revature.rideshare.user.repository.UserRoleRepository;

@Service
public class UserRoleService {
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	public UserRole findByType(String type) {
		return userRoleRepository.findByTypeIgnoreCase(type);
	}
}
