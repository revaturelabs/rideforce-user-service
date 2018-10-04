package com.revature.rideforce.user.json;

/**
 * An interface for classes that can convert string values into corresponding
 * instances of an {@link EnumLike} class.
 * 
 * @param <T> the type to which string values are resolved
 * @since Iteration1: 10/01/2018
 */
public interface EnumLikeResolver<T extends EnumLike> {
	/**
	 * Resolves a value to a corresponding {@link EnumLike} instance.
	 * 
	 * @param value the value to resolve. This value is compared case-insensitively.
	 * @return the instance of type {@code T} corresponding to this value
	 * @throws IllegalArgumentException if the given value does not correspond to
	 *                                  any instance of {@code T}
	 */
	T resolve(String value);
}
