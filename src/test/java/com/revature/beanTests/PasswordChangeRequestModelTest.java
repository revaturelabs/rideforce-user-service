package com.revature.beanTests;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.assertj.core.api.Assertions;
import org.hibernate.validator.HibernateValidator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.revature.rideforce.user.beans.PasswordChangeRequest;

public class PasswordChangeRequestModelTest {

	private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setupValidatorFactory () {
		localValidatorFactory = new LocalValidatorFactoryBean();
		localValidatorFactory.setProviderClass(HibernateValidator.class);
		localValidatorFactory.afterPropertiesSet();
	}
	
	@Test
	public void validPasswordChangeRequestHasNoViolations() {
		PasswordChangeRequest passChangeReq = new PasswordChangeRequest("password", "newpassword");
		Set<ConstraintViolation<PasswordChangeRequest>> violations = localValidatorFactory.validate(passChangeReq);
		Assertions.assertThat(violations.size()).isEqualTo(0);
	}
	
	@Test
	public void emptyOldPasswordChangeRequestHasViolations() {
		PasswordChangeRequest passChangeReq = new PasswordChangeRequest("", "newpassword");
		Set<ConstraintViolation<PasswordChangeRequest>> violations = localValidatorFactory.validate(passChangeReq);
		Assertions.assertThat(violations.size()).isEqualTo(1);
	}
	
	@Test
	public void emptyNewPasswordChangeRequestHasViolations() {
		PasswordChangeRequest passChangeReq = new PasswordChangeRequest("password", "");
		Set<ConstraintViolation<PasswordChangeRequest>> violations = localValidatorFactory.validate(passChangeReq);
		Assertions.assertThat(violations.size()).isEqualTo(1);
	}
	
}
