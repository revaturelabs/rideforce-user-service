package com.revature.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.revature.models.Role;
import com.revature.repos.RoleRepo;

public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleRepo rr;
	
	@Override
	public Role createRole(Role role) {
		rr.save(role);
		return role;
	}

	@Override
	public Role getRoleById(int id) {
		return rr.findById(id).get();
	}

	@Override
	public Role getByRname(String rname) {
		return rr.findByRname(rname);
	}

	@Override
	public List<Role> getAllRoles() {
		return (List<Role>)rr.findAll();
	}

}
