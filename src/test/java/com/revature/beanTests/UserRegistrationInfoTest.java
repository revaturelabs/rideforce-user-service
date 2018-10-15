package com.revature.beanTests;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.assertj.core.api.Assertions;
import org.hibernate.validator.HibernateValidator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.beans.UserRegistrationInfo;

public class UserRegistrationInfoTest {

	private LocalValidatorFactoryBean localValidatorFactory;

	private UserRegistrationInfo uri; 
	
	@Before
    public void setupValidatorFactory () {
        localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
    }
	
	@Test
	public void allNullUserRegInfo() {
		uri = new UserRegistrationInfo();
		uri.setUser(null);
		Validator validator = localValidatorFactory.getValidator();
		Set<ConstraintViolation<UserRegistrationInfo>> violations = validator.validate(uri);
		Assertions.assertThat(violations.size()).isEqualTo(3);
	}
	
	@Test
	public void emptyPasswordAndKeyUserRegInfo() {
		uri = new UserRegistrationInfo(null, "", "");
		Validator validator = localValidatorFactory.getValidator();
		Set<ConstraintViolation<UserRegistrationInfo>> violations = validator.validate(uri);
		Assertions.assertThat(violations.size()).isEqualTo(3);
	}
	
	@Test
	public void nullPasswordTest() {
		uri = new UserRegistrationInfo(new User(), null, "key");
		Set<ConstraintViolation<UserRegistrationInfo>> violations = localValidatorFactory.validate(uri);
		int counter = 0;
		
		for(ConstraintViolation<UserRegistrationInfo> v: violations) {
			String propertyPath = v.getPropertyPath().toString();
			if (propertyPath.equals("password")) {
				counter++;
			}
		}
		Assertions.assertThat(counter).isEqualTo(1);
	}
	
	@Test
	public void emptyPasswordTest() {
		uri = new UserRegistrationInfo(new User(), "", "key");
		Set<ConstraintViolation<UserRegistrationInfo>> violations = localValidatorFactory.validate(uri);
		int counter = 0;
		
		for(ConstraintViolation<UserRegistrationInfo> v: violations) {
			String propertyPath = v.getPropertyPath().toString();
			if (propertyPath.equals("password")) {
				counter++;
			}
		}
		Assertions.assertThat(counter).isEqualTo(1);
	}

	@Test
	public void nullKeyTest() {
		uri = new UserRegistrationInfo(new User(), "password", null);
		Set<ConstraintViolation<UserRegistrationInfo>> violations = localValidatorFactory.validate(uri);
		int counter = 0;
		
		for(ConstraintViolation<UserRegistrationInfo> v: violations) {
			String propertyPath = v.getPropertyPath().toString();
			if (propertyPath.equals("registrationKey")) {
				counter++;
			} 
		}
		Assertions.assertThat(counter).isEqualTo(1);
	}
	
	@Test
	public void emptyKeyTest() {
		uri = new UserRegistrationInfo(new User(), "password", "");
		Set<ConstraintViolation<UserRegistrationInfo>> violations = localValidatorFactory.validate(uri);
		int counter = 0;
		
		for(ConstraintViolation<UserRegistrationInfo> v: violations) {
			String propertyPath = v.getPropertyPath().toString();
			if (propertyPath.equals("registrationKey")) {
				counter++;
			} 
		}
		Assertions.assertThat(counter).isEqualTo(1);
	}
}
