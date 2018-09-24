package com.revature.rideforce.user.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.rideforce.user.beans.UserRole;
import com.revature.rideforce.user.exceptions.DuplicateRoleException;
import com.revature.rideforce.user.exceptions.EntityConflictException;
import com.revature.rideforce.user.repository.UserRoleRepository;

/**
 * A service for accessing role data.
 */
@Service
public class UserRoleService extends CrudService<UserRole> {
	private UserRoleRepository userRoleRepository;

	@Autowired
	public UserRoleService(UserRoleRepository userRoleRepository) {
		super(userRoleRepository);
		this.userRoleRepository = userRoleRepository;
	}

	public UserRole findByType(String type) {
		return userRoleRepository.findByTypeIgnoreCase(type);
	}

	@Override
	protected void throwOnConflict(UserRole obj) throws EntityConflictException {
		UserRole existing = findByType(obj.getType());
		if (existing != null && existing.getId() != obj.getId()) {
			throw new DuplicateRoleException(obj.getType());
		}
	}
}
