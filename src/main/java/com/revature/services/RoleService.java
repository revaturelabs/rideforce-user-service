package com.revature.services;

import java.util.List;
import com.revature.models.Role;

public interface RoleService {
	/**
	 * Create a {@link Role} and save to database.
	 * 
	 * @param role {@link Role} to create.
	 * @return newly created {@link Role}.
	 */
	public Role createRole(Role role);
	
	/**
	 * Update a {@link Role} and save to database
	 * 
	 * @param role {@link Role} to update.
	 * @return updated {@link Role}.
	 */
	public Role updateRole(Role role);

	/**
	 * Get a {@link Role} by ID
	 * 
	 * @param id ID of {@link Role}.
	 * @return {@link Role} with specified ID
	 */
	public Role getRoleById(int id);

	/**
	 * Get a {@link Role} filtered by the role name. Should only return one due to
	 * unique constraint.
	 * 
	 * @param rname role name of {@link Role}.
	 * @return {@link Role} specified by role name.
	 */
	public Role getByRname(String rname);

	/**
	 * Gets all {@link Role} records from database.
	 * 
	 * @return List of all {@link Role}s in the database.
	 */
	public List<Role> getAllRoles();
	
	/**
	 * Delete a {@link Role} from the database by id
	 * @param rid - id of {@link Role} 
	 * @return boolean - whether the deletion was successful
	 */
	public boolean deleteRole(int rid);
	
}
