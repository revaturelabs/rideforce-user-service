package com.revature.beanTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.assertj.core.api.Assertions;
import org.hibernate.validator.HibernateValidator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.revature.rideforce.user.beans.UserCredentials;

@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class UserCredentialsTest {

	private LocalValidatorFactoryBean localValidatorFactory;

	private UserCredentials uc;
	private UserCredentials uc2;

	@Before
	public void setupValidatorFactory() {
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

	@Test
	public void userCredentialHashCodeTest() {
		uc = new UserCredentials("email@gmail.com", "password");
		assertEquals(uc.hashCode(), -1629853329);
	}

	@Test
	public void userCredentialEqualsTest1() {
		uc = new UserCredentials("email@gmail.com", "password");
		uc2 = new UserCredentials("email@gmail.com", "p4ssw0rd");
		assertThat(!uc.equals(uc2));
	}

	@Test
	public void userCredentialEqualsTest2() {
		uc = new UserCredentials("email@gmail.com", "password");
		uc2 = new UserCredentials("email@gmail.com", "password");
		assertThat(uc.equals(uc2));
	}

	@Test
	public void userCredentialEqualsTest3() {
		uc = new UserCredentials("email123@gmail.com", "password");
		uc2 = new UserCredentials("email@gmail.com", "p4ssw0rd");
		assertThat(!uc.equals(uc2));
	}

	@Test
	public void userCredentialEqualsTest4() {
		uc = new UserCredentials("email123@gmail.com", "password");
		uc2 = new UserCredentials("email@gmail.com", "password");
		assertThat(!uc.equals(uc2));
	}

	@Test
	public void toStringTest() {
		uc = new UserCredentials("email@gmail.com", "password");
		assertThat(uc.toString().equals("UserCredentials [email=email@gmail.com, password=password]"));
	}
}