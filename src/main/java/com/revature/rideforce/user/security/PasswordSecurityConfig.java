package com.revature.rideforce.user.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * dependency for PasswordEncoder uses BCryptPasswordEncoder
 * @author clpeng
 * @since Iteration1 10/01/2018
 *
 */
@Configuration
public class PasswordSecurityConfig {
	/**
	 * encodes password with BCryptPasswordEncoder from Spring Security
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
}
