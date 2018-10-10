package com.revature.beanTests;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.assertj.core.api.Assertions;
import org.hibernate.validator.HibernateValidator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.revature.rideforce.user.beans.UserCredentials;



public class UserCredentialsTest {

	private LocalValidatorFactoryBean localValidatorFactory;

	private UserCredentials uc;
	
	@Before
    public void setupValidatorFactory () {
        localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
    }
	
	@Test
	public void userCredentialNullFieldsTest() {
		uc = new UserCredentials();
		Assertions.assertThat(uc.getEmail()).isEqualTo(null);
		Assertions.assertThat(uc.getPassword()).isEqualTo(null);
		Validator validator = localValidatorFactory.getValidator();
		Set<ConstraintViolation<UserCredentials>> violations = validator.validate(uc);
		Assertions.assertThat(violations.size()).isEqualTo(2);
	}
	
	@Test
	public void userCredentialValidFieldsTest() {
		uc = new UserCredentials("email@gmail.com", "password");
		Assertions.assertThat(uc.getEmail()).isEqualTo("email@gmail.com");
		Assertions.assertThat(uc.getPassword()).isEqualTo("password");
		Validator validator = localValidatorFactory.getValidator();
		Set<ConstraintViolation<UserCredentials>> violations = validator.validate(uc);
		Assertions.assertThat(violations.size()).isEqualTo(0);
	}
}