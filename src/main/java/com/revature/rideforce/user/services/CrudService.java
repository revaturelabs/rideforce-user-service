package com.revature.rideforce.user.services;

import java.util.List;
import java.lang.invoke.MethodHandles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.rideforce.user.beans.Identifiable;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.controllers.CrudController;
import com.revature.rideforce.user.exceptions.EntityConflictException;
import com.revature.rideforce.user.exceptions.PermissionDeniedException;
import lombok.extern.slf4j.Slf4j;

/**
 * An abstract class implementing the basic methods of a CRUD service. The
 * purpose of this class is to enforce consistency in naming and method
 * availability and to provide inherited behavior and documentation which would
 * be otherwise tedious to write. Additionally, this may be used in conjunction
 * with {@link CrudController} to simplify construction of controller classes.
 * 
 * @param <T> the type of object on which this service acts
 * @since Iteration1 10/01/2018
 */
@Slf4j
public abstract class CrudService<T extends Identifiable> {
	static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	@Autowired
	protected AuthenticationService authenticationService;

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
	 * @throws PermissionDeniedException if the currently logged-in user does not
	 *                                   have permission to access all objects
	 */
	public List<T> findAll() throws PermissionDeniedException {
		if (!canFindAll()) {
      String msg = "Permission denied to find all objects";
      log.info("User found by authenticationService.getCurrentUser(): {}", authenticationService.getCurrentUser());
			throw new PermissionDeniedException(msg);
		}
		return repository.findAll();
	}

	/**
	 * Finds a single instance of type {@code T}.
	 * 
	 * @param id the ID of the instance to find
	 * @return the instance that was found, or {@code null} if none were found
	 * @throws PermissionDeniedException if the currently logged-in user does not
	 *                                   have permission to access the object
	 */
	public T findById(int id) throws PermissionDeniedException {
		T obj = repository.findById(id).orElse(null);
		if (!canFindOne(obj)) {
			throw new PermissionDeniedException("Permission denied to find requested object.");
		}
		return obj;
	}

	/**
	 * Adds a new instance of type {@code T} to persistent storage.
	 * 
	 * @param obj the instance to add
	 * @return the instance after being saved, which may contain additional
	 *         information (such as a generated ID)
	 * @throws EntityConflictException   if the instance would conflict with another
	 *                                   instance that is already persistent
	 * @throws PermissionDeniedException if the currently logged-in user does not
	 *                                   have permission to add the object
	 */
	public T add(T obj) throws EntityConflictException, PermissionDeniedException {
		if (obj == null) {
			throw new IllegalArgumentException("Cannot add a null object.");
		}
		// Ensure that a new entity is created.
		obj.setId(0);
		if (!canAdd(obj)) {
			throw new PermissionDeniedException("Permission denied to add object.");
		}
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
	 * @throws EntityConflictException   if the proposed changes to the instance
	 *                                   would conflict with another instance
	 * @throws PermissionDeniedException if the currently logged-in user does not
	 *                                   have permission to save the object
	 */
	public T save(T obj) throws EntityConflictException, PermissionDeniedException {
		if (obj == null) {
			throw new IllegalArgumentException("Cannot save null object.");
		}
		if (!canSave(obj)) {
			throw new PermissionDeniedException("Permission denied to save object.");
		}
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

	/**
	 * Determines whether the given user can retrieve a list of all objects. The
	 * default implementation is to allow access to all logged-in users.<br>
	 * This is only the helper method though, should always be used within - {@linkplain #canFindAll()}
	 * 
	 * @param user the user requesting permission (or {@code null} if
	 *             unauthenticated)
	 * @return whether the given user is allowed to retrieve a list of all objects
	 */
	protected boolean canFindAll(User user) {
		return user != null;
	}

	/**
	 * Determines whether the currently logged-in user can retrieve a list of all
	 * objects. This is a helper method wrapping {@link #canFindAll(User)}.
	 * 
	 * @return whether the currently logged-in user is allowed to retrieve a list of
	 *         all objects
	 */
	protected final boolean canFindAll() {
		return canFindAll(authenticationService.getCurrentUser());
	}

	/**
	 * Determines whether the user can retrieve one particular object. The default
	 * implementation is to allow all logged-in users to retrieve all objects.
	 * 
	 * @param user the user requesting permission (or {@code null} if
	 *             unauthenticated)
	 * @param obj  the requested object, which may be {@code null}
	 * @return whether the user can retrieve the given object
	 */
	protected boolean canFindOne(User user, T obj) {
		return user != null;
	}

	/**
	 * Determines whether the currently logged-in user can retrieve one particular
	 * object. This is a helper method wrapping
	 * {@link #canFindOne(User, Identifiable)}.
	 * 
	 * @param obj the requested object, which may be {@code null}
	 * @return whether the currently logged-in user can retrieve the given object
	 */
	protected final boolean canFindOne(T obj) {
		return canFindOne(authenticationService.getCurrentUser(), obj);
	}

	/**
	 * Determines whether the user can add a particular object. The default
	 * implementation is to allow only admins to add arbitrary objects
	 * and deny any addition to other users.
	 * 
	 * @param user the user requesting permission (or {@code null} if
	 *             unauthenticated)
	 * @param obj  the object to be added, which will never be {@code null}
	 * @return whether the user can add the given object
	 */
	protected boolean canAdd(User user, T obj) {
		return user != null && user.isAdmin();
	}

	/**
	 * Determines whether the currently logged-in user can add a particular object.
	 * This is a helper method wrapping {@link #canAdd(User, Identifiable)}.
	 * 
	 * @param obj the object to be added, which will never be {@code null}
	 * @return whether the currently logged-in user can add the given object
	 */
	protected final boolean canAdd(T obj) {
		return canAdd(authenticationService.getCurrentUser(), obj);
	}

	/**
	 * Determines whether the user can save a particular object. The default
	 * implementation is to allow only admins to save arbitrary objects
	 * and deny any save permissions to other users.
	 * 
	 * @param user the user requesting permission (or {@code null} if
	 *             unauthenticated)
	 * @param obj  the object to be saved, which will never be {@code null}
	 * @return whether the user can save the given object
	 */
	protected boolean canSave(User user, T obj) {
		return user != null && user.isAdmin();
	}

	/**
	 * Determines whether the currently logged-in user can save a particular object.
	 * This is a helper method wrapping {@link #canSave(User, Identifiable)}.
	 * 
	 * @param obj the object to be saved, which will never be {@code null}
	 * @return whether the currently logged-in user can save the given object
	 */
	protected final boolean canSave(T obj) {
		return canSave(authenticationService.getCurrentUser(), obj);
	}
}
