package com.revature.beanTests;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.assertj.core.api.Assertions;
import org.hibernate.validator.HibernateValidator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.revature.rideforce.user.beans.Office;

public class OfficeModelTest {
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setupValidatorFactory () {
		localValidatorFactory = new LocalValidatorFactoryBean();
		localValidatorFactory.setProviderClass(HibernateValidator.class);
		localValidatorFactory.afterPropertiesSet();
	}
	
	@Test
	public void validOfficeModelHasNoViolations() {
		Office theOffice = new Office(1, "Michael Scott", "5125 Validation Ln.");
		Set<ConstraintViolation<Office>> violations = localValidatorFactory.validate(theOffice);
		Assertions.assertThat(violations.size()).isEqualTo(0);
	}
	
	@Test
	public void invalidIdOnOfficeHasViolations() {
		Office theOffice = new Office(-1, "Michael Scott", "5125 Validation Ln.");
		Set<ConstraintViolation<Office>> violations = localValidatorFactory.validate(theOffice);
		Assertions.assertThat(violations.size()).isEqualTo(1);
	}
	
	@Test
	public void emptyNameOnOfficeHasViolations() {
		Office theOffice = new Office(1, "", "5125 Validation Ln.");
		Set<ConstraintViolation<Office>> violations = localValidatorFactory.validate(theOffice);
		Assertions.assertThat(violations.size()).isEqualTo(1);
	}
	
	@Test
	public void emptyAddressOnOfficeHasViolations() {
		Office theOffice = new Office(1, "Michael Scott", "");
		Set<ConstraintViolation<Office>> violations = localValidatorFactory.validate(theOffice);
		Assertions.assertThat(violations.size()).isEqualTo(1);
	}
	
	
}
