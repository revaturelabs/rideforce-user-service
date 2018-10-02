package com.revature.beanTests;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.assertj.core.api.Assertions;
import org.hibernate.validator.HibernateValidator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.revature.rideforce.user.beans.UserRole;

public class UserRoleTest {

	private LocalValidatorFactoryBean localValidatorFactory;
	
	private UserRole ur;
	
	@Before
    public void setupValidatorFactory () {
        localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
    }
	
	@Test
	public void userRoleNullTest() {
		ur = new UserRole();
		Validator validator = localValidatorFactory.getValidator();
		Set<ConstraintViolation<UserRole>> violations = validator.validate(ur);
		Assertions.assertThat(violations.size()).isEqualTo(2);
	}
	
	@Test
	public void userRoleInvalidIdTest() {
		ur = new UserRole(0, "test");
		Validator validator = localValidatorFactory.getValidator();
		Set<ConstraintViolation<UserRole>> violations = validator.validate(ur);
		Assertions.assertThat(violations.size()).isEqualTo(1);
	}
	
	@Test
	public void userRoleInvalidTypeTest() {
		ur = new UserRole(1, null);
		Validator validator = localValidatorFactory.getValidator();
		Set<ConstraintViolation<UserRole>> violations = validator.validate(ur);
		Assertions.assertThat(violations.size()).isEqualTo(1);
	}
}
