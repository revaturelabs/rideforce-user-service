package com.revature.beanTests;

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

import com.revature.rideforce.user.beans.UserRole;

@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class UserRoleTest {

	private LocalValidatorFactoryBean localValidatorFactory;

	private UserRole ur;
	private UserRole ur2;

	@Before
	public void setupValidatorFactory() {
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

	@Test
	public void equalsTest() {
		ur = new UserRole(0, "test");
		ur2 = new UserRole(0, "test");
		Assertions.assertThat(ur.equals(ur2));
	}

	@Test
	public void equalsTest2() {
		ur = new UserRole(0, "test");
		ur2 = new UserRole(1, "test");
		Assertions.assertThat(!ur.equals(ur2));
	}

	@Test
	public void equalsTest3() {
		ur = new UserRole(0, "test");
		ur2 = new UserRole(1, "test2");
		Assertions.assertThat(!ur.equals(ur2));
	}

	@Test
	public void equalsTest4() {
		ur = new UserRole(0, "test");
		ur2 = new UserRole(0, "test2");
		Assertions.assertThat(!ur.equals(ur2));
	}

	@Test
	public void equalsTest5() {
		ur = new UserRole(0, null);
		ur2 = new UserRole(1, "test");
		Assertions.assertThat(!ur.equals(ur2));
	}
}
