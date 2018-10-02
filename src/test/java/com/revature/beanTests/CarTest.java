package com.revature.beanTests;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.revature.rideforce.user.beans.Car;
import com.revature.rideforce.user.beans.User;

public class CarTest {

	@Autowired
	private LocalValidatorFactoryBean localValidatorFactory;
	
	@Test
	public void testCreationOfAValidCar() {
		Car car = new Car(101, new User(), "Honda", "Accord", 2001);
		Assert.assertEquals(car.getId(), 101);
		Assertions.assertThat(car.getMake()).isEqualTo("Honda");
		Assertions.assertThat(car.getModel()).isEqualTo("Accord");
		Assertions.assertThat(car.getYear()).isEqualTo(2001);
		Assertions.assertThat(car.getOwner()).isNotNull();
	}
	
	@Test
	public void testValidatorWorksOnAValidCar() {
		Validator validator = localValidatorFactory.getValidator();
        Set<ConstraintViolation<Car>> violations = validator.validate(member);
        
	}
	
	
	
	
	
	
}
