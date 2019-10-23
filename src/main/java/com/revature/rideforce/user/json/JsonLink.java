package com.revature.rideforce.user.json;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * An annotation marking a field of object type that should be represented in
 * JSON as a link.
 * 
 * This annotation can also be applied to a collection of object values to
 * represent each item in the collection as a link.
 * @since Iteration1: 10/01/2018
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface JsonLink {
	/**
	 * The {@link LinkResolver} to be used to resolve links in JSON data. An
	 * instance of this class must be available as a bean.
	 */
	Class<? extends LinkResolver<?>> value();
}
