package com.revature.rideforce.user.json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import com.revature.rideforce.user.beans.Car;
import com.revature.rideforce.user.repository.CarRepository;


/** 
 * Its resolve(String link) method will return a Car from the database based on the url link. Uses AntPathMatcher from Spring framework 
 * as the tool to extract information needed from link urls.<p>
 * <strong>Members:</strong><br>
 * {@link org.springframework.util.AntPathMatcher AntPathMatcher} matcher - singleton path matcher<br>
 * {@link com.revature.rideforce.user.repository.CarRepository CarRepository} carRepository
 * 
 * @since Iteration1: 10/01/2018
 * @author clpeng
 *
 */
@Service
public class CarLinkResolver implements LinkResolver<Car> {
	private static final AntPathMatcher matcher = new AntPathMatcher();

	@Autowired
	private CarRepository carRepository;

	/* (non-Javadoc)
	 * @see com.revature.rideforce.user.json.LinkResolver#resolve(java.lang.String)
	 */
	/**
	 * @throws NumberFormatException - for using Integer.parseInt() on a path variable that isn't a number
	 * @throws IllegalArgumentException - for alerting the user the arguments passed in from the link are illegal
	 */
	@Override
	public Car resolve(String link) {
		try {
			int id = Integer.parseInt(matcher.extractUriTemplateVariables("/cars/{id}", link).get("id"));
			return carRepository.findById(id);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(link + " is not a valid car link.");
		}
	}
}
