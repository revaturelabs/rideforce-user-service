package com.revature.beanTests;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.assertj.core.api.Assertions;
import org.hibernate.validator.HibernateValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.revature.rideforce.user.beans.Car;
import com.revature.rideforce.user.beans.User;

public class CarTest {

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
	
	@Before
	public void setupValidatorFactory () {
		localValidatorFactory = new LocalValidatorFactoryBean();
		localValidatorFactory.setProviderClass(HibernateValidator.class);
		localValidatorFactory.afterPropertiesSet();
	}
	
	@Test
	public void testNullOwnerIsViolationOnACar() {
		Car car = new Car(101, null, "Honda", "Accord", 2001);
        Set<ConstraintViolation<Car>> violations = localValidatorFactory.validate(car);
        Assert.assertTrue(violations.size() == 1);
	}
	
	@Test
	public void testInvalidIdIsViolationOnACar() {
		// Set the owner as null to avoid the violations on the owner property of the Car class
		Car car = new Car(101, null, "Honda", "Accord", 2001);
		car.setId(0);
		Set<ConstraintViolation<Car>> violations = localValidatorFactory.validate(car);
		// the violations of a null owner and an invalid id
		Assert.assertTrue(violations.size() == 2);
	}
	
	@Test
	public void testInvalidMakeOnACar() {
		// Set the owner as null to avoid the violations on the owner property of the Car class
		Car car = new Car(101, null, "", "Accord", 2001);
		Set<ConstraintViolation<Car>> violations = localValidatorFactory.validate(car);
		// the violations of a null owner and an empty make
		Assert.assertTrue(violations.size() == 2);
	}
	
	@Test
	public void testInvalidModelOnACar() {
		// Set the owner as null to avoid the violations on the owner property of the Car class
		Car car = new Car(101, null, "Honda", "", 2001);
		Set<ConstraintViolation<Car>> violations = localValidatorFactory.validate(car);
		// the violations of a null owner and an empty make
		Assert.assertTrue(violations.size() == 2);
	}
	
	
	
}
