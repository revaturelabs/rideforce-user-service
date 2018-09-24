package com.revature.rideshare.user.services;

import java.util.List;

import com.revature.rideshare.user.controllers.CrudController;
import com.revature.rideshare.user.exceptions.EntityConflictException;

/**
 * An interface abstracting the basic methods of a CRUD service. The purpose of
 * this interface is to enforce consistency in naming and method availability
 * and to provide inherited documentation which would be otherwise tedious to
 * write. Additionally, this may be used in conjunction with
 * {@link CrudController} to simplify construction of controller classes.
 * 
 * @param <T> the type of object on which this service acts
 */
public interface CrudService<T> {
	/**
	 * Finds all instances of type {@code T} under consideration.
	 * 
	 * @return all instances that are known to the service
	 */
	List<T> findAll();

	/**
	 * Finds a single instance of type {@code T}.
	 * 
	 * @param id the ID of the instance to find
	 * @return the instance that was found, or {@code null} if none were found
	 */
	T findById(int id);

	/**
	 * Adds a new instance of type {@code T} to persistent storage.
	 * 
	 * @param obj the instance to add
	 * @return the instance after being saved, which may contain additional
	 *         information (such as a generated ID)
	 * @throws EntityConflictException if the instance would conflict with another
	 *                                 instance that is already persistent
	 */
	T add(T obj) throws EntityConflictException;

	/**
	 * Saves updates to an existing instance of type {@code T} to persistent
	 * storage.
	 * 
	 * @param obj the instance to add. The ID of this instance must correspond to
	 *            one already in persistent storage.
	 * @return the instance after being saved, which may contain additional
	 *         information
	 * @throws EntityConflictException if the proposed changes to the instance would
	 *                                 conflict with another instance
	 */
	T save(T obj) throws EntityConflictException;
}
