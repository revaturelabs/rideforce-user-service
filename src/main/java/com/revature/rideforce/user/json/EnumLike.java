package com.revature.rideforce.user.json;

/**
 * An interface that specifies that some class is meant to function as an enum,
 * but cannot actually be an enum due to extensibility restrictions. For
 * example, a database might represent an enum as a table of ID-description
 * pairs; such an "enum" could not be represented as a true enum in Java,
 * because the complete list of values cannot be enumerated at compile-time (and
 * may even be extensible at run-time).
 * 
 * This is usually used in conjunction with an {@link EnumLikeResolver}, which
 * performs the task of converting string values into {@code EnumLike} values.
 * 
 * Implementors of this interface should also override {@link Object#hashCode()
 * hashCode} and {@link Object#equals(Object) equals} so that different objects
 * representing the same value should be treated as equal.
 * 
 * @since Iteration1: 10/01/2018
 */
public interface EnumLike {
	/**
	 * Gets the string representation of this {@link EnumLike} value.
	 * 
	 * @return the string representation of this value. Representations must be
	 *         unique (case-insensitively) among all objects representing the same
	 *         value.
	 */
	String toEnumString();
}
