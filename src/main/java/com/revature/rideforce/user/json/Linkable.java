package com.revature.rideforce.user.json;

import java.net.URI;

/**
 * An interface indicating that an instance of a class can be linked to. For
 * example, a user object might have a link of "/users/43", which would be the
 * URI for obtaining a representation of that object.
 * @since Iteration1: 10/01/2018
 */
public interface Linkable {
	/**
	 * Gets the URI corresponding to this object eg. ResponseEntity
	 * 
	 * @return the URI corresponding to this object, pointing to an endpoint
	 * where a representation of this object can be obtained (e.g. "/users/43").
	 */
	public URI toUri();

	/**
	 * Gets a link to this object.
	 * 
	 * @return a link (URI) pointing to an endpoint where a representation of this
	 *         object can be obtained (e.g. "/users/43")
	 */
	public default String toLink() {
		return toUri().toString();
	}
}
