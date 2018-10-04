package com.revature.rideforce.user.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.rideforce.user.beans.Office;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.beans.UserRole;
import com.revature.rideforce.user.exceptions.EmailAlreadyUsedException;
import com.revature.rideforce.user.exceptions.EntityConflictException;
import com.revature.rideforce.user.exceptions.PermissionDeniedException;
import com.revature.rideforce.user.repository.UserRepository;

@Service
public class UserService extends CrudService<User> {
  private final static Logger logger = LoggerFactory.getLogger(UserService.class);
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	public UserService(UserRepository userRepository) {
		super(userRepository);
		this.userRepository = userRepository;
	}

	public User findByEmail(String email) throws PermissionDeniedException {
		User found = userRepository.findByEmail(email);
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
    logger.info("user saved");
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
