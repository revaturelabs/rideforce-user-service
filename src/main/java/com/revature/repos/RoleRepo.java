package com.revature.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.revature.models.Role;


@Repository
public interface RoleRepo extends CrudRepository<Role,Integer> {
	
	List<Role> findByName(String rname);
}
