package com.revature.rideforce.user.security;

import java.time.Period;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.repository.UserRepository;

/**
 * A class for generating and parsing JSON Web Tokens.
 * 
 * @author Ian Johnson, Vien Ly
 * @since Iteration1 10/01/2018
 */
@Service
public class LoginTokenProvider extends JwtProvider {
	@Autowired
	private UserRepository userRepository;

	/**
	 * Generates a new JWT which can be used to authenticate the user with the given
	 * ID.
	 * 
	 * @param userId the ID of the user to be authenticated by the token
	 * @return the JWT corresponding to the given user ID
	 */
	public String generateToken(int userId) {
		return generateToken(Integer.toString(userId), Period.ofDays(1));
	}

	/**
	 * Converts the given JWT into an authentication token that can be stored as the
	 * authentication principal in Spring's SecurityContext.
	 * 
	 * @param token the JWT to convert
	 * @return the authentication token, or null if the given JWT was invalid
	 */
	public Authentication getAuthentication(String token) {
		try {
			int userId = Integer.parseInt(getSubject(token));
			User user = userRepository.findById(userId);
			if (user == null) {
				return null;
			}
			return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
		} catch (NumberFormatException e) {
			return null;
		} 
	}

	/**
	 * Extracts the JWT from the given request.
	 * 
	 * @param request the request from which to extract the token
	 * @return the token, or null if the request did not contain one
	 */
	public String extractToken(HttpServletRequest request) {
		String auth = request.getHeader("Authorization");
		if (auth == null || !auth.startsWith("Bearer ")) {
			return null;
		}
		return auth.substring("Bearer ".length());
	}
}