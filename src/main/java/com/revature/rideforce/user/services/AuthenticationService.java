package com.revature.rideforce.user.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.beans.UserCredentials;
import com.revature.rideforce.user.beans.UserRegistrationInfo;
import com.revature.rideforce.user.exceptions.EntityConflictException;
import com.revature.rideforce.user.exceptions.InvalidCredentialsException;
import com.revature.rideforce.user.exceptions.InvalidRegistrationKeyException;
import com.revature.rideforce.user.exceptions.PermissionDeniedException;
import com.revature.rideforce.user.repository.UserRepository;
import com.revature.rideforce.user.security.LoginTokenProvider;
import com.revature.rideforce.user.security.RegistrationTokenProvider;

/**
 * The service used to handle authentication (i.e. logging in, creating new
 * users, getting information about the current user).
 */
@Service
public class AuthenticationService {
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;

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
		User found = userRepository.findByEmail(credentials.getEmail());
		if (found == null) {
			throw new InvalidCredentialsException();
		}
		if (!passwordEncoder.matches(credentials.getPassword(), found.getPassword())) {
			throw new InvalidCredentialsException();
		}
		return loginTokenProvider.generateToken(found.getId());
	}

	/**
	 * Registers a new user with the given information.
	 * 
	 * @param info the registration information for the new user
	 * @return the user that was created
	 * @throws InvalidRegistrationKeyException if the provided registration key was
	 *                                         invalid
	 * @throws EntityConflictException         if the given email is already used by
	 *                                         another user
	 * @throws PermissionDeniedException       if the currently logged-in user does
	 *                                         not have permission to create the
	 *                                         desired user (e.g. if an
	 *                                         unauthenticated user attempts to
	 *                                         create an admin)
	 */
	public User register(UserRegistrationInfo info)
			throws InvalidRegistrationKeyException, EntityConflictException, PermissionDeniedException {
		// Make sure that the registration key is valid.
		if (!registrationTokenProvider.isValid(info.getRegistrationKey())) {
			throw new InvalidRegistrationKeyException();
		}
		String passwordHash = passwordEncoder.encode(info.getPassword());
		info.getUser().setPassword(passwordHash);
		return userService.add(info.getUser());
	}

	/**
	 * Gets the {@link User} object corresponding to the currently authenticated
	 * user.
	 * 
	 * @return the currently authenticated user, or {@code null} if no user is
	 *         logged in (according to the authentication information stored in the
	 *         {@link SecurityContextHolder}
	 */
	public User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null || !auth.isAuthenticated() || !(auth.getPrincipal() instanceof User)) {
			return null;
		}
		return (User) auth.getPrincipal();
	}
}
