package com.revature.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.revature.models.Role;

@Repository
public interface RoleRepo extends CrudRepository<Role,Integer> {

}
