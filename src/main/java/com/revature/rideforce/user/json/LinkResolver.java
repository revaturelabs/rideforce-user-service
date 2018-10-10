package com.revature.rideforce.user.json;

/**
 * An interface for classes that can resolve links, providing the inverse
 * functionality of the {@link Linkable} interface.
 * 
 * <p> Functional interface(only one method resolve(String link) with actual implementation found in classes like<br>
 * {@linkplain CarLinkResolver}, {@linkplain ContactTypeResolver}, {@linkplain UserLinkResolver}, etc.
 * 
 * @param <T> the type to which links will be resolved
 * @since Iteration1: 10/01/2018
 * @author clpeng(wrote 2nd paragraph, let her know if anything incorrect)
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
	T resolve(String link);   //actual implementation in classes like CarLinkResolver, ContactTypeResolver, which implement this interface
}
