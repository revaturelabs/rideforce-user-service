package com.revature.rideshare.user.json;

/**
 * An interface indicating that an instance of a class can be linked to. For
 * example, a user object might have a link of "/users/43", which would be the
 * URI for obtaining a representation of that object.
 */
public interface Linkable {
	/**
	 * Gets a link to this object.
	 * 
	 * @return a link (URI) pointing to an endpoint where a representation of this
	 *         object can be obtained (e.g. "/users/43")
	 */
	public String toLink();
}
