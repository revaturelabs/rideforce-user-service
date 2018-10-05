package com.revature.rideforce.user.services;
import java.lang.invoke.MethodHandles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * The service used to handle authentication, that is, logging in, creating new
 * users, getting information about the current user.
 */
@Service
public class AuthenticationService {
  final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
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
    logger.info("Authenticating user credentials");
    logger.debug("credentials.email(): {} ", credentials.getEmail()); //find solution for logging sensitive data; possibly dbappender
    logger.debug("credentials.password: {} ", credentials.getPassword());
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
      logger.info("Attempting to register user");
      logger.debug(info.getRegistrationKey());
			throw new InvalidRegistrationKeyException();
		}
    logger.info("User registered successfully");
    logger.info("Hashing password");
		String passwordHash = passwordEncoder.encode(info.getPassword());
    logger.debug("passwordHash: {}", passwordHash);
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
    logger.info("Getting current user from Authentication");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    logger.debug("Authentication value: {}", auth);
		if (auth == null || !auth.isAuthenticated() || !(auth.getPrincipal() instanceof User)) {
      logger.debug("User is null"); //TODO: split if statements
			return null;
		}
    logger.debug("User authenticated successfully");
		return (User) auth.getPrincipal();
	}
}
