package com.revature.rideforce.user.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.beans.UserRole;
import com.revature.rideforce.user.exceptions.DuplicateRoleException;
import com.revature.rideforce.user.exceptions.EntityConflictException;
import com.revature.rideforce.user.exceptions.PermissionDeniedException;
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

	public UserRole findByType(String type) throws PermissionDeniedException {
		UserRole found = userRoleRepository.findByTypeIgnoreCase(type);
		if (!canFindOne(found)) {
			throw new PermissionDeniedException("Permission denied to find one object.");
		}
		return found;
	}

	@Override
	protected void throwOnConflict(UserRole obj) throws EntityConflictException {
		UserRole existing = userRoleRepository.findByTypeIgnoreCase(obj.getType());
		if (existing != null && existing.getId() != obj.getId()) {
			throw new DuplicateRoleException(obj.getType());
		}
	}
	
	@Override
	protected boolean canFindAll(User user) {
		return true;
	}
	
	@Override
	protected boolean canFindOne(User user, UserRole obj) {
		return true;
	}
}
