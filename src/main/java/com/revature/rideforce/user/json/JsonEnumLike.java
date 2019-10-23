package com.revature.rideforce.user.json;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * An annotation marking an enum-like bean property that should be represented
 * in JSON by its value (as determined by {@link EnumLike#toEnumString()}).
 * 
 * This annotation can also be applied to a collection of objects to represent
 * each item by its value.
 * @since Iteration1: 10/01/2018
 */
@Retention(RUNTIME)
@Target(FIELD)  
public @interface JsonEnumLike {   //'@interface' keyword makes it an annotation 
	/**
	 * The {@link EnumLikeResolver} to be used to resolve string values in JSON
	 * data. An instance of this class must be available as a bean.
	 */
	Class<? extends EnumLikeResolver<?>> value();
}
