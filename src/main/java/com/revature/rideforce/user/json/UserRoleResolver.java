package com.revature.rideforce.user.json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.rideforce.user.beans.UserRole;
import com.revature.rideforce.user.repository.UserRoleRepository;

@Service
public class UserRoleResolver implements EnumLikeResolver<UserRole> {
	@Autowired
	private UserRoleRepository userRoleRepository;

	@Override
	public UserRole resolve(String value) {
		UserRole role = userRoleRepository.findByTypeIgnoreCase(value);
		if (role == null) {
			throw new IllegalArgumentException(value + " is not a valid role.");
		}
		return role;
	}
}
