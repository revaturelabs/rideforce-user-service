package com.revature.beanTests;

import java.net.URI;
import java.net.URISyntaxException;
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
	private Car testCar;
	
	@Before
	public void setupValidatorFactory () {
		localValidatorFactory = new LocalValidatorFactoryBean();
		localValidatorFactory.setProviderClass(HibernateValidator.class);
		localValidatorFactory.afterPropertiesSet();
		
		testCar = new Car();
		testCar.setYear(1996);
		testCar.setOwner(new User());
		testCar.setId(33);
	}
	
	@Test
	public void testCreationOfAValidCar() {
		Car car = new Car(101, new User(), "Honda", "Accord", 2001);
		Assert.assertEquals(101, car.getId());
		Assertions.assertThat(car.getMake()).isEqualTo("Honda");
		Assertions.assertThat(car.getModel()).isEqualTo("Accord");
		Assertions.assertThat(car.getYear()).isEqualTo(2001);
		Assertions.assertThat(car.getOwner()).isNotNull();
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
		Assert.assertTrue(violations.size() > 0);
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
	@Test
	public void toUriTest() throws URISyntaxException {
		Assert.assertTrue(this.testCar.toUri().equals(new URI("/cars/"+testCar.getId())));
	}
	
	
	
}
