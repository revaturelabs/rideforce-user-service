package com.revature.rideforce.user.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordSecurityUtil {
	private static PasswordEncoder passwordEncoder;
	
	private PasswordSecurityUtil() {}
	public static PasswordEncoder getPasswordEncoder()
	{
		if(PasswordSecurityUtil.passwordEncoder == null)
			PasswordSecurityUtil.passwordEncoder = new BCryptPasswordEncoder();
		return PasswordSecurityUtil.passwordEncoder;
	}
	
	
}
