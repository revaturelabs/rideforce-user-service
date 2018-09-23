package com.revature.rideshare.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.rideshare.user.beans.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
	UserRole findByTypeIgnoreCase(String type);
	public UserRole findById(int id);
}
