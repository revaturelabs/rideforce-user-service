package com.revature.rideforce.user.controllers;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.rideforce.user.beans.Identifiable;
import com.revature.rideforce.user.beans.ResponseError;
import com.revature.rideforce.user.exceptions.EntityConflictException;
import com.revature.rideforce.user.exceptions.PermissionDeniedException;
import com.revature.rideforce.user.json.Linkable;
import com.revature.rideforce.user.services.CrudService;

import lombok.extern.slf4j.Slf4j;
/**
 * An abstract base class for CRUD controllers that takes care of common CRUD
 * method implementations.
 */
@Slf4j
@PreAuthorize("hasAnyRole('ROLE_TRAINER','ROLE_ADMIN','ROLE_RIDER', 'ROLE_DRIVER')")
public abstract class CrudController<T extends Identifiable & Linkable> {
	static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	protected CrudService<T> service;

	/**
	 * Constructs a new {@link CrudController}.
	 * 
	 * @param service the {@link CrudService} to use for CRUD operations
	 */
	protected CrudController(CrudService<T> service) {
		this.service = service;
	}

	/**
	 * Finds all objects.
	 * 
	 * @return a {@link ResponseEntity} containing all the objects found
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> findAll() {
		try {
			return ResponseEntity.ok(service.findAll());
		} catch (PermissionDeniedException e) {
			return new ResponseError(e).toResponseEntity(HttpStatus.FORBIDDEN);
		}
	}

	/**
	 * Finds an object by ID.
	 * 
	 * @param id the ID of the object to find
	 * @return a {@link ResponseEntity} with the object that was found or an error
	 *         if none was found
	 */
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> findById(@PathVariable("id") int id) {
		try {
			log.info("Finding Id: " + id);
		T found = service.findById(id);
		return found == null
				? new ResponseError("Instance with ID " + id + " not found.").toResponseEntity(HttpStatus.NOT_FOUND)
				: ResponseEntity.ok(found);
		} catch (PermissionDeniedException e) {
			return new ResponseError(e).toResponseEntity(HttpStatus.FORBIDDEN);
		}
	}

	/**
	 * Adds the given object.
	 * 
	 * @param obj the object to add
	 * @return a {@link ResponseEntity} with the new object or an error if there was
	 *         a conflict
	 */
	@PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> add(@RequestBody @Valid T obj) {
		try {
			T created = service.add(obj);
			return ResponseEntity.created(created.toUri()).body(created);
		} catch (EntityConflictException e) {
			return new ResponseError(e).toResponseEntity(HttpStatus.CONFLICT);
		} catch (PermissionDeniedException e) {
			return new ResponseError(e).toResponseEntity(HttpStatus.FORBIDDEN);
		}
	}

	/**
	 * Saves the given object.
	 * 
	 * @param obj the object to save
	 * @return a {@link ResponseEntity} with the saved object or an error if there
	 *         was a conflict
	 */
	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> save(@RequestBody @Valid T obj) {
		try {
			return ResponseEntity.ok(service.save(obj));
		} catch (EntityConflictException e) {
			return new ResponseError(e).toResponseEntity(HttpStatus.CONFLICT);
		} catch (PermissionDeniedException e) {
			return new ResponseError(e).toResponseEntity(HttpStatus.FORBIDDEN);
		}
	}
}
