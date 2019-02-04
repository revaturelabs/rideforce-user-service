package com.revature.rideforce.user.services;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.revature.rideforce.user.beans.Office;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.beans.UserRole;
import com.revature.rideforce.user.exceptions.EmailAlreadyUsedException;
import com.revature.rideforce.user.exceptions.EntityConflictException;
import com.revature.rideforce.user.exceptions.PermissionDeniedException;
import com.revature.rideforce.user.repository.UserRepository;

/**
 * inherits methods from {@linkplain CrudService} but adds methods more specific to the {@linkplain User} model 
 * like password updating, finding by User's fields email & office, adding certain users only if the logged in user is the correct type.
 * <p><strong>Member Variables</strong><br>
 * {@linkplain UserRepository} userRepository<br>
 * {@linkplain PasswordEncoder} passwordEncoder
 * @author clpeng
 * @since Iteration1 10/01/2018
 */
@Service
public class UserService extends CrudService<User> {
	@Autowired
	private Logger log;
	private UserRepository userRepository;

	
	/**
	 * constructor injects repository dependency
	 * @param userRepository {@linkplain UserRepository} object that will be injected as the service's dao layer
	 */
	@Autowired
	public UserService(UserRepository userRepository) {
		super(userRepository);
		this.userRepository = userRepository;
	}

	/**
	 * finds the correct {@linkplain User} after being provided the email
	 * @param email <code>String</code> input that's supposed to be a user's email
	 * @return {@link User} that email belongs to
	 * @throws PermissionDeniedException
	 */
	public User findByEmail(String email) throws PermissionDeniedException {
		log.info("Attempting to retrieve user by email from userRepository");
		log.debug("User email: {}", email);
		User found = userRepository.findByEmail(email);

		log.info("User {} found", found);

		
		if (!canFindOne(found)) {   //if u scroll down, rn this just rtns true (because of matching service needs)
			throw new PermissionDeniedException("Permission denied to get user by email.");
		}
		
		return userRepository.findByEmail(email);
	}
	

	/**
	 * find the correct {@linkplain User} after being provided the user's office and role
	 * @param office the location of the user, {@linkplain Office} object
	 * @param role the role of the user, {@linkplain UserRole} object
	 * @return the user working at specified office and role, a {@linkplain User} object
	 * @throws PermissionDeniedException
	 */
	public List<User> findByOfficeAndRole(Office office, UserRole role) throws PermissionDeniedException {
		if (!canFindAll()) {
			throw new PermissionDeniedException("Permission denied to get users by office and role.");
		}
		return userRepository.findByOfficeAndRole(office, role);
	}
	
	/**
	 * delete the User object if there is someone logged in, and that person is either the same User or an admin user
	 * @param user User to be deleted from db
	 * @throws PermissionDeniedException for user that is not allowed to do the transaction
	 */
	public void deleteUser(User user) throws PermissionDeniedException
	{
		User loggedInUser = authenticationService.getCurrentUser();
		if (loggedInUser == null || (!loggedInUser.isAdmin() && loggedInUser.getId() != user.getId())) 
			throw new PermissionDeniedException("Cannot delete this user's account.");
		userRepository.delete(user);
		
	}

	/* (non-Javadoc)
	 * @see com.revature.rideforce.user.services.CrudService#throwOnConflict(com.revature.rideforce.user.beans.Identifiable)
	 */
	@Override
	protected void throwOnConflict(User obj) throws EntityConflictException {
		User existing = userRepository.findByEmail(obj.getUsername());
		if (existing != null && existing.getId() != obj.getId()) {
			throw new EmailAlreadyUsedException(obj.getUsername());
		}
	}
	
	/* (non-Javadoc)
	 * @see com.revature.rideforce.user.services.CrudService#canFindAll(com.revature.rideforce.user.beans.User)
	 */
	@Override
	protected boolean canFindAll(User user) {
		// This is not ideal, but needs to be this way until the matching
		// service can pass along authorization headers.
		return true;
	}
	
	/* (non-Javadoc)
	 * @see com.revature.rideforce.user.services.CrudService#canFindOne(com.revature.rideforce.user.beans.User, com.revature.rideforce.user.beans.Identifiable)
	 */
	@Override
	protected boolean canFindOne(User user, User obj) {
		// This is not ideal, but needs to be this way until the matching
		// service can pass along authorization headers.
		return true;
	}
	
	/* (non-Javadoc)
	 * @see com.revature.rideforce.user.services.CrudService#canAdd(com.revature.rideforce.user.beans.User, com.revature.rideforce.user.beans.Identifiable)
	 */
	@Override
	protected boolean canAdd(User user, User obj) {
		// Make sure users can't add other users with elevated permissions.
		if (obj.isAdmin() || obj.isTrainer()) {
			return user != null && user.isAdmin();
		}
		return true;
	}
	
	/* (non-Javadoc)
	 * @see com.revature.rideforce.user.services.CrudService#canSave(com.revature.rideforce.user.beans.User, com.revature.rideforce.user.beans.Identifiable)
	 */
	@Override
	protected boolean canSave(User user, User obj) {
		// Any user can update themself; only admins can update any user.
		return user != null && (user.getId() == obj.getId() || user.isAdmin());
	}
}
