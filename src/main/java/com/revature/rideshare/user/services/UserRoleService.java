package com.revature.rideshare.user.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.rideshare.user.beans.UserRole;
import com.revature.rideshare.user.exceptions.DuplicateRoleException;
import com.revature.rideshare.user.repository.UserRoleRepository;

@Service
public class UserRoleService {
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	public UserRole findByType(String type) {
		return userRoleRepository.findByTypeIgnoreCase(type);
	}
	
	public UserRole add(UserRole role) throws DuplicateRoleException {
		role.setId(0);
		if (findByType(role.getType()) != null) {
			throw new DuplicateRoleException(role.getType());
		}
		return userRoleRepository.save(role);
	}
	
	public UserRole save(UserRole role) {
		return userRoleRepository.save(role);
	}

	public Object getAll() {
		return userRoleRepository.findAll();
	}

	public UserRole findById(int id) {
		return userRoleRepository.findById(id);
	}
}
