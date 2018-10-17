package com.revature.rideforce.user.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.beans.UserCredentials;
import com.revature.rideforce.user.beans.UserRegistrationInfo;
import com.revature.rideforce.user.exceptions.DisabledAccountException;
import com.revature.rideforce.user.exceptions.EmptyPasswordException;
import com.revature.rideforce.user.exceptions.EntityConflictException;
import com.revature.rideforce.user.exceptions.InvalidCredentialsException;
import com.revature.rideforce.user.exceptions.InvalidRegistrationKeyException;
import com.revature.rideforce.user.exceptions.PasswordRequirementsException;
import com.revature.rideforce.user.exceptions.PermissionDeniedException;
import com.revature.rideforce.user.repository.UserRepository;
import com.revature.rideforce.user.security.LoginTokenProvider;
import com.revature.rideforce.user.security.RegistrationTokenProvider;

import lombok.extern.slf4j.Slf4j;

/**
 * The service used to handle authentication, that is, logging in, creating new
 * users, getting information about the current user.
 */
@Slf4j
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
	 * @throws DisabledAccountException	   if the account is disabled (by admin)
	 */
	public String authenticate(UserCredentials credentials) throws InvalidCredentialsException, DisabledAccountException {
		User found = userRepository.findByEmail(credentials.getEmail());
		log.info("Authenticating user credentials");
		log.debug("credentials.email(): {} ", credentials.getEmail()); //find solution for logging sensitive data; possibly dbappender
		log.debug("credentials.password: {} ", credentials.getPassword());
		if (found == null) {
			throw new InvalidCredentialsException();
		}
		if (!passwordEncoder.matches(credentials.getPassword(), found.getPassword())) {
			throw new InvalidCredentialsException();
		}
		if (found.isActive().equals("DISABLED")) {
			throw new DisabledAccountException();
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
	 * @throws EmptyPasswordException          Password in the {@linkplain UserRegistrationInfo} must be non empty
	 * @throws PasswordRequirementsException   if the password entered does not
	 * 										   meet the requirements specified
	 */
	public User register(UserRegistrationInfo info)
			throws InvalidRegistrationKeyException, EntityConflictException, PermissionDeniedException, EmptyPasswordException, PasswordRequirementsException {
		if(info == null) {
			throw new InvalidRegistrationKeyException();
		}
		// Make sure that the registration key token is valid.
		if (!registrationTokenProvider.isValid(info.getRegistrationKey())) {
			log.info("Attempting to register user");
			log.debug(info.getRegistrationKey());
			throw new InvalidRegistrationKeyException();
		}
		// Make sure password meets requirements.
		if(!passwordIsValid(info.getPassword())) {
			log.info("Password length violation");
			throw new PasswordRequirementsException();
		}
		log.info("User registered successfully");
		log.info("Hashing password");
		info.getUser().setPassword(info.getPassword());   //hashing will be done in setPassword()
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
		log.info("Getting current user from Authentication");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		log.debug("Authentication value: {}", auth);

		if (auth == null || !auth.isAuthenticated() || !(auth.getPrincipal() instanceof User)) {
			log.debug("User is null"); 
			return null;
		}
    
		log.debug("User authenticated successfully");
		return (User) auth.getPrincipal();
	}
	
	/**
	 * Method for password requirements validation. Can be adjusted to accommodate future requirements.
	 * 
	 * @param password to be validated
	 * @return true if valid password
	 */
	private boolean passwordIsValid(String password) {
		return (password.length() >= 8 && password.length() <= 16);
	}
}
