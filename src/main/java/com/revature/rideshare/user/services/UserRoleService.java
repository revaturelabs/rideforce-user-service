package com.revature.rideshare.user.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.rideshare.user.beans.UserRole;
import com.revature.rideshare.user.exceptions.DuplicateRoleException;
import com.revature.rideshare.user.repository.UserRoleRepository;

/**
 * A service for accessing role data.
 */
@Service
public class UserRoleService implements CrudService<UserRole> {
	@Autowired
	private UserRoleRepository userRoleRepository;

	@Override
	public List<UserRole> findAll() {
		return userRoleRepository.findAll();
	}

	@Override
	public UserRole findById(int id) {
		return userRoleRepository.findById(id);
	}
	
	public UserRole findByType(String type) {
		return userRoleRepository.findByTypeIgnoreCase(type);
	}
	
	@Override
	public UserRole add(UserRole role) throws DuplicateRoleException {
		// Ensure that a new role is created.
		role.setId(0);
		if (findByType(role.getType()) != null) {
			throw new DuplicateRoleException(role.getType());
		}
		return userRoleRepository.save(role);
	}
	
	@Override
	public UserRole save(UserRole role) throws DuplicateRoleException {
		UserRole existing = findByType(role.getType());
		if (existing != null && existing.getId() != role.getId()) {
			throw new DuplicateRoleException(role.getType());
		}
		return userRoleRepository.save(role);
	}
}
