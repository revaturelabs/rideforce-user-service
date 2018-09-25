package com.revature.rideforce.user.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.rideforce.user.beans.Office;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.beans.UserRole;
import com.revature.rideforce.user.exceptions.EmailAlreadyUsedException;
import com.revature.rideforce.user.exceptions.EntityConflictException;
import com.revature.rideforce.user.exceptions.PermissionDeniedException;
import com.revature.rideforce.user.repository.UserRepository;

@Service
public class UserService extends CrudService<User> {
	private UserRepository userRepository;

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

	@Override
	protected void throwOnConflict(User obj) throws EntityConflictException {
		User existing = userRepository.findByEmail(obj.getEmail());
		if (existing != null && existing.getId() != obj.getId()) {
			throw new EmailAlreadyUsedException(obj.getEmail());
		}
	}
}
