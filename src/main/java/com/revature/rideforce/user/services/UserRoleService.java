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

  /**
    Searches for UserRose by type String 
    @param String type to search repository
    @throws PermissionDeniedException if user has no permission to view the UserRole
    @return UserRole
    */
	public UserRole findByType(String type) throws PermissionDeniedException {
		UserRole found = userRoleRepository.findByTypeIgnoreCase(type);
		if (!canFindOne(found)) {
			throw new PermissionDeniedException("Permission denied to find one object.");
		}
		return found;
	}

  /**
    Exception that is thrown if attempt to persist duplicate UserRole
    @param UserRole
    @throws EntityConflictException
    */
	@Override
	protected void throwOnConflict(UserRole obj) throws EntityConflictException {
		UserRole existing = userRoleRepository.findByTypeIgnoreCase(obj.getType());
		if (existing != null && existing.getId() != obj.getId()) {
			throw new DuplicateRoleException(obj.getType());
		}
	}
	
  /**
    Checks if User has permission to search for UserRoles. 
    @param User
    @return boolean true for now
    */
	@Override
	protected boolean canFindAll(User user) {
		return true;
	}
	
  /**
    Checks if User has permission to search for a UserRole
    @param User
    @return boolean true for now
    */
	@Override
	protected boolean canFindOne(User user, UserRole obj) {
		return true;
	}
}
