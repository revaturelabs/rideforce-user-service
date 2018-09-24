package com.revature.rideforce.user.services;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.rideforce.user.beans.Identifiable;
import com.revature.rideforce.user.controllers.CrudController;
import com.revature.rideforce.user.exceptions.EntityConflictException;

/**
 * An abstract class implementing the basic methods of a CRUD service. The
 * purpose of this class is to enforce consistency in naming and method
 * availability and to provide inherited behavior and documentation which would
 * be otherwise tedious to write. Additionally, this may be used in conjunction
 * with {@link CrudController} to simplify construction of controller classes.
 * 
 * @param <T> the type of object on which this service acts
 */
public abstract class CrudService<T extends Identifiable> {
	protected JpaRepository<T, Integer> repository;

	/**
	 * Constructs a new {@link CrudService}, using the given {@link JpaRepository}
	 * as a base.
	 * 
	 * @param repository the repository to use for CRUD operations
	 */
	protected CrudService(JpaRepository<T, Integer> repository) {
		this.repository = repository;
	}

	/**
	 * Finds all instances of type {@code T} under consideration.
	 * 
	 * @return all instances that are known to the service
	 */
	public List<T> findAll() {
		return repository.findAll();
	}

	/**
	 * Finds a single instance of type {@code T}.
	 * 
	 * @param id the ID of the instance to find
	 * @return the instance that was found, or {@code null} if none were found
	 */
	public T findById(int id) {
		return repository.findById(id).orElse(null);
	}

	/**
	 * Adds a new instance of type {@code T} to persistent storage.
	 * 
	 * @param obj the instance to add
	 * @return the instance after being saved, which may contain additional
	 *         information (such as a generated ID)
	 * @throws EntityConflictException if the instance would conflict with another
	 *                                 instance that is already persistent
	 */
	public T add(T obj) throws EntityConflictException {
		// Ensure that a new entity is created.
		obj.setId(0);
		throwOnConflict(obj);
		return repository.save(obj);
	}

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
	public T save(T obj) throws EntityConflictException {
		throwOnConflict(obj);
		return repository.save(obj);
	}

	/**
	 * A helper method which throws an instance of {@link EntityConflictException}
	 * when the given object conflicts with one that is already persistent. The
	 * default implementation is to do nothing.
	 * 
	 * @param obj the object to check for conflict
	 * @throws EntityConflictException if the given object conflicts with one that
	 *                                 is already persistent
	 */
	protected void throwOnConflict(T obj) throws EntityConflictException {
	}
}
