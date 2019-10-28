package com.revature.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.revature.models.Role;
import com.revature.repos.RoleRepo;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleRepo rr;
	
	@Override
	public Role createRole(Role role) {
		if (role == null || (role.getId() != null && rr.existsById(role.getId()))) {
			return null;
		}
		return rr.save(role);
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

	@Override
	public Role updateRole(Role role) {
		if (role == null || role.getId() == null || !rr.existsById(role.getId())) {
			return null;
		}
		return rr.save(role);
	}
	
	@Override
	public boolean deleteRole(int rid) {
		try {
			rr.deleteById(rid);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

}
