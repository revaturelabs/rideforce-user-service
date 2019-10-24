package com.revature.services;

import com.revature.models.Role;

public interface RoleService {
	
	public Role createRole(Role role);
	
	public Role getRoleById(int id);
	
	public Role getByRname(String rname);
	

}
