package com.revature.rideforce.user.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.beans.UserRole;
import com.revature.rideforce.user.exceptions.DuplicateRoleException;
import com.revature.rideforce.user.exceptions.EntityConflictException;
import com.revature.rideforce.user.exceptions.PermissionDeniedException;
import com.revature.rideforce.user.repository.OfficeRepository;
import com.revature.rideforce.user.repository.UserRoleRepository;

/**
 * A service for accessing role data.
 * <p><strong>Member Variables</strong><br>
 * {@linkplain UserRoleRepository} userRoleRepository
 * @author Iteration1, clpeng
 * @since Iteration1 10/01/2018
 */
@Service
public class UserRoleService extends CrudService<UserRole> {
	private UserRoleRepository userRoleRepository;

	/**
	 * The constructor, which injects the repository as a dependency
	 * @param userRoleRepository dependency that contains data logic/ DAO layer 
	 */
	@Autowired
	public UserRoleService(UserRoleRepository userRoleRepository) {
		super(userRoleRepository);
		this.userRoleRepository = userRoleRepository;
	}

	/**
	 * verifies whether or not the user has access to the type, then finds the correct {@linkplain UserRole} from the database based on it
	 * @param type <code>String</code> type of user to be searched for in the db. 
	 * @return the found {@linkplain UserRole} from the inputted type
	 * @throws PermissionDeniedException
	 */
	public UserRole findByType(String type) throws PermissionDeniedException {
		UserRole found = userRoleRepository.findByTypeIgnoreCase(type);
		if (!canFindOne(found)) {
			throw new PermissionDeniedException("Permission denied to find one object.");
		}
		return found;
	}

	/* (non-Javadoc)
	 * @see com.revature.rideforce.user.services.CrudService#throwOnConflict(com.revature.rideforce.user.beans.Identifiable)
	 */
	@Override
	protected void throwOnConflict(UserRole obj) throws EntityConflictException {
		UserRole existing = userRoleRepository.findByTypeIgnoreCase(obj.getType());
		if (existing != null && existing.getId() != obj.getId()) {
			throw new DuplicateRoleException(obj.getType());
		}
	}
	
	/* (non-Javadoc)
	 * @see com.revature.rideforce.user.services.CrudService#canFindAll(com.revature.rideforce.user.beans.User)
	 */
	@Override
	protected boolean canFindAll(User user) {
		return true;
	}
	
	/* (non-Javadoc)
	 * @see com.revature.rideforce.user.services.CrudService#canFindOne(com.revature.rideforce.user.beans.User, com.revature.rideforce.user.beans.Identifiable)
	 */
	@Override
	protected boolean canFindOne(User user, UserRole obj) {
		return true;
	}
}
