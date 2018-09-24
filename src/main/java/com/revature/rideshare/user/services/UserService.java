package com.revature.rideshare.user.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.revature.rideshare.user.beans.Office;
import com.revature.rideshare.user.beans.User;
import com.revature.rideshare.user.beans.UserCredentials;
import com.revature.rideshare.user.beans.UserRegistrationInfo;
import com.revature.rideshare.user.beans.UserRole;
import com.revature.rideshare.user.exceptions.EmailAlreadyUsedException;
import com.revature.rideshare.user.exceptions.InvalidCredentialsException;
import com.revature.rideshare.user.exceptions.InvalidRegistrationKeyException;
import com.revature.rideshare.user.repository.UserRepository;
import com.revature.rideshare.user.security.LoginTokenProvider;
import com.revature.rideshare.user.security.RegistrationTokenProvider;

@Service
public class UserService {
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LoginTokenProvider loginTokenProvider;

	@Autowired
	private RegistrationTokenProvider registrationTokenProvider;

	/**
	 * Authenticates a user with the given credentials, returning a JSON Web Token
	 * that can be used for future authentication.
	 * 
	 * @param credentials the user's credentials (email and password)
	 * @return a JWT for future authentication
	 * @throws InvalidCredentialsException if the given credentials are invalid
	 *                                     (wrong email or password)
	 */
	public String authenticate(UserCredentials credentials) throws InvalidCredentialsException {
		User found = findByEmail(credentials.getEmail());
		if (found == null) {
			throw new InvalidCredentialsException();
		}
		if (!passwordEncoder.matches(credentials.getPassword(), found.getPassword())) {
			throw new InvalidCredentialsException();
		}
		return loginTokenProvider.generateToken(found.getId());
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public User findById(int id) {
		return userRepository.findById(id);
	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public List<User> findByOfficeAndRole(Office office, UserRole role) {
		return userRepository.findByOfficeAndRole(office, role);
	}

	/**
	 * Registers a new user with the given information.
	 * 
	 * @param info the registration information for the new user
	 * @return the user that was created
	 * @throws InvalidRegistrationKeyException if the provided registration key was
	 *                                         invalid
	 * @throws EmailAlreadyUsedException       if the given email is already used by
	 *                                         another user
	 */
	public User register(UserRegistrationInfo info) throws InvalidRegistrationKeyException, EmailAlreadyUsedException {
		// Make sure that we create a new user.
		info.getUser().setId(0);
		// Make sure that the registration key is valid.
		if (!registrationTokenProvider.isValid(info.getRegistrationKey())) {
			throw new InvalidRegistrationKeyException();
		}
		User newUser = info.getUser();
		if (findByEmail(newUser.getEmail()) != null) {
			throw new EmailAlreadyUsedException(newUser.getEmail());
		}
		String passwordHash = passwordEncoder.encode(info.getPassword());
		newUser.setPassword(passwordHash);
		return userRepository.save(newUser);
	}

	public User save(User user) throws EmailAlreadyUsedException {
		User existing = findByEmail(user.getEmail());
		if (existing != null && existing.getId() != user.getId()) {
			throw new EmailAlreadyUsedException(user.getEmail());
		}
		return userRepository.save(user);
	}
}
