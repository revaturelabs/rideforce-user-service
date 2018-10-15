package com.revature.rideforce.user.security;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.rideforce.user.services.UserService;

/**
 * use in AuthenticationService to make the specific login recovery token
 * @author clpeng
 *
 */
@Service  																
public class LoginRecoveryTokenProvider extends JwtProvider{
	//similar to the registration token
	
	//subject to pass into JwtProvider's generateToken(subject, duration)
//	public static final String SUBJECT_RECOVERY = "RECOVERY";
	
	public String generateToken(Integer id)
	{
		//make a token for recovery. User can reset password within 30 min, and token information/subject is just the user Id
		return super.generateToken(String.valueOf(id), Duration.ofMinutes(30)); //could use "this" instead of super, but super is more readable
	}
	
	/**
	 * use this method when you want to see the token later and validate it didnt expire/is a real token with valid subject
	 * @return <code>int</code> representing the User id we're gonna have password recovery for
	 */
	public Integer checkTokenForUser(String token) {
		//using JwtProvider's getSubject(), which returns null for expired or invalid tokens. JwtProvider is an abstract class we made that uses some of
		//built in Spring Security stuff
		return ( ( super.getSubject(token) ) != null ? Integer.parseInt( super.getSubject(token) ) : null );
		//I can't rtn a user because i'd have to be logged in xD	
		
	}
	
}
