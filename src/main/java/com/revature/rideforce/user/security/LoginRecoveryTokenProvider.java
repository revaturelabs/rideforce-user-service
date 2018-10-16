package com.revature.rideforce.user.security;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.beans.forms.LoginRecoveryTokenSubject;
import com.revature.rideforce.user.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * use in AuthenticationService to make the specific login recovery token
 * @author clpeng
 *
 */
@Slf4j
@Service  																
public class LoginRecoveryTokenProvider extends JwtProvider{
	//similar to the registration token
	@Autowired
	private UserRepository userRepository;
	private ObjectMapper objectMapper = new ObjectMapper();
	
	public String generateToken(int id)
	{
		User user = userRepository.findById(id);
		LoginRecoveryTokenSubject subject = new LoginRecoveryTokenSubject(id, user.getPassword(), Date.from(Instant.now()));
		String jsonString = "";
		try {
			jsonString = objectMapper.writeValueAsString(subject);
		} catch (JsonProcessingException e) {
			log.error("ObjectMapper couldn't write object into a string. Null subject");
		}
		
		//make a token for recovery. User can reset password within 30 min, and token information/subject is just the user Id
		return super.generateToken(jsonString, Duration.ofMinutes(30)); //could use "this" instead of super, but super is more readable
	}
	
	/**
	 * use this method when you want to see the token later and validate it didnt expire/is a real token with valid subject
	 * @return <code>int</code> representing the User id we're gonna have password recovery for
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public LoginRecoveryTokenSubject checkTokenForUser(String token)  {
		//using JwtProvider's getSubject(), which returns null for expired or invalid tokens. JwtProvider is an abstract class we made that uses some of
		//built in Spring Security stuff
		String jsonString = super.getSubject(token);
		if(jsonString == null) 		//token expired or is invalid
			return null;
		
		LoginRecoveryTokenSubject subject;
		try {
			subject = objectMapper.readValue(jsonString, LoginRecoveryTokenSubject.class);
		} catch (IOException e) {
			return null;
		} 
		
		User user = userRepository.findById( subject.getId() );
		if(user.getPassword().equals(subject.getPassword())) 		//password doesnt match token's recorded p/w
			return subject;
		return null;
	}
	
}
