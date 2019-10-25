package com.revature.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.revature.models.Role;
import com.revature.models.User;

@Repository
public interface UserRepo extends CrudRepository<User, Integer>{

	/**
     * This method is able to find all users (should be just one) 
     * which have the email as defined in the parameter. 
     * 
     * @param email an email which you would like to find a user by
     * @return      the User that has this email.
     */
	public User findByEmail(String email);
	
	public User findByEmailAndPassword(String email, String password);
	
	public List<User> findByRolesIn(List<Role> roles);
	
}
