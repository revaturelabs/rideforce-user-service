package com.revature.rideforce.user.services;

import java.util.List;

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
import com.revature.rideforce.user.repository.UserRoleRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * inherits methods from {@linkplain CrudService} but adds methods more specific to the {@linkplain User} model 
 * like password updating, finding by User's fields email & office, adding certain users only if the logged in user is the correct type.
 * <p><strong>Member Variables</strong><br>
 * {@linkplain UserRepository} userRepository<br>
 * {@linkplain PasswordEncoder} passwordEncoder
 * @author clpeng
 * @since Iteration1 10/01/2018
 */
@Slf4j
@Service
public class UserService extends CrudService<User> {
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	public UserService(UserRepository userRepository) {
		super(userRepository);
		this.userRepository = userRepository;
    log.info("UserService created");
    log.debug("UserService.userRepository initialized to: {}", userRepository);
	}

	public User findByEmail(String email) throws PermissionDeniedException {
		log.info("Attempting to retrieve user by email from userRepository");
		log.debug("User email: {}", email);
		User found = userRepository.findByEmail(email);

    log.info("User {} found", found);

		if (!canFindOne(found)) {
			throw new PermissionDeniedException("Permission denied to get user by email.");
		}
		return userRepository.findByEmail(email);
	}

	public List<User> findByOfficeAndRole(Office office, UserRole role) throws PermissionDeniedException {
		if (!canFindAll()) {
			throw new PermissionDeniedException("Permission denied to get users by office and role.");
		}
		return userRepository.findByOfficeAndRole(office, role);
	}
	
	public void updatePassword(User user, String oldPassword, String newPassword) throws PermissionDeniedException {
		User loggedIn = authenticationService.getCurrentUser();
		// Check permission to update password. Admins can update anyone's
		// password, provided they know the old password.
		if (loggedIn == null || (!loggedIn.isAdmin() && loggedIn.getId() != user.getId())) {
			throw new PermissionDeniedException("Cannot change this user's password.");
		}
		if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
			throw new PermissionDeniedException("Old password is incorrect.");
		}
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
    log.info("updated user: " + user);
	}

	@Override
	protected void throwOnConflict(User obj) throws EntityConflictException {
		User existing = userRepository.findByEmail(obj.getEmail());
		if (existing != null && existing.getId() != obj.getId()) {
			throw new EmailAlreadyUsedException(obj.getEmail());
		}
	}
	
	@Override
	protected boolean canFindAll(User user) {
		// This is not ideal, but needs to be this way until the matching
		// service can pass along authorization headers.
		return true;
	}
	
	@Override
	protected boolean canFindOne(User user, User obj) {
		// This is not ideal, but needs to be this way until the matching
		// service can pass along authorization headers.
		return true;
	}
	
	@Override
	protected boolean canAdd(User user, User obj) {
		// Make sure users can't add other users with elevated permissions.
		if (obj.isAdmin() || obj.isTrainer()) {
			return user != null && user.isAdmin();
		}
		return true;
	}
	
	@Override
	protected boolean canSave(User user, User obj) {
		// Any user can update themself; only admins can update any user.
		return user != null && (user.getId() == obj.getId() || user.isAdmin());
	}
}
