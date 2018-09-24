package com.revature.rideforce.user.json;

/**
 * An interface for classes that can resolve links, providing the inverse
 * functionality of the {@link Linkable} interface.
 * 
 * @param <T> the type to which links will be resolved
 */
public interface LinkResolver<T extends Linkable> {
	/**
	 * Resolves the given link to the object to which it points.
	 * 
	 * @param link the link to resolve
	 * @return the resolved object, or {@code null} if the link is syntactically
	 *         valid but does not point to an existent object
	 * @throws IllegalArgumentException if the given link is invalid
	 */
	T resolve(String link);
}
