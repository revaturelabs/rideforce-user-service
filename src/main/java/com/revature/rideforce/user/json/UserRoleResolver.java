package com.revature.rideforce.user.json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.rideforce.user.beans.UserRole;
import com.revature.rideforce.user.services.UserRoleService;

@Service
public class UserRoleResolver implements EnumLikeResolver<UserRole> {
	@Autowired
	private UserRoleService userRoleService;

	@Override
	public UserRole resolve(String value) {
		UserRole role = userRoleService.findByType(value);
		if (role == null) {
			throw new IllegalArgumentException(value + " is not a valid role.");
		}
		return role;
	}
}
