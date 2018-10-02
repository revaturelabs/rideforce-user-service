package com.revature.beanTests;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.assertj.core.api.Assertions;
import org.hibernate.validator.HibernateValidator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.revature.rideforce.user.beans.ContactType;

public class ContactTypeTest {
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setupValidatorFactory () {
		localValidatorFactory = new LocalValidatorFactoryBean();
		localValidatorFactory.setProviderClass(HibernateValidator.class);
		localValidatorFactory.afterPropertiesSet();
	}
	
	@Test
	public void validContactTypeHasNoViolations() {
		ContactType contactType = new ContactType(2, "type");
		Set<ConstraintViolation<ContactType>> violations = localValidatorFactory.validate(contactType);
		Assertions.assertThat(violations.size()).isEqualTo(0);
	}
	
	@Test
	public void invalidIdOnContactTypeHasViolations() {
		ContactType contactType = new ContactType(-1, "type");
		Set<ConstraintViolation<ContactType>> violations = localValidatorFactory.validate(contactType);
		Assertions.assertThat(violations.size()).isEqualTo(1);
	}
	
	@Test
	public void emptyTypeOnContactTypeHasViolations() {
		ContactType contactType = new ContactType(2, "");
		Set<ConstraintViolation<ContactType>> violations = localValidatorFactory.validate(contactType);
		Assertions.assertThat(violations.size()).isEqualTo(1);
	}
	
}
