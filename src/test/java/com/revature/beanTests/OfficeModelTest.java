package com.revature.beanTests;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.assertj.core.api.Assertions;
import org.hibernate.validator.HibernateValidator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.revature.rideforce.user.beans.Office;

@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
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
	
	@Test
	public void officeEqualsTest1() {
		Office office = new Office(1, "name", "address");
		Office office2 = new Office(1, "name", "address");
		Assertions.assertThat(office.equals(office2));
	}
	
	@Test
	public void officeEqualsTest2() {
		Office office = new Office();
		Office office2 = new Office();
		Assertions.assertThat(office.equals(office2));
	}
	
	@Test
	public void officeEqualsTest3() {
		Office office = new Office(1, "name", "address");
		Office office2 = new Office(2, "name", "address");
		Assertions.assertThat(!office.equals(office2));
	}
	
	@Test
	public void officeEqualsTest4() {
		Office office = new Office(1, "name", "address");
		Office office2 = new Office(1, null, "address");
		Assertions.assertThat(!office.equals(office2));
	}
	
	@Test
	public void officeEqualsTest5() {
		Office office = new Office(1, "name", null);
		Office office2 = new Office(1, "name", "address");
		Assertions.assertThat(!office.equals(office2));
	}
	
	@Test
	public void officeEqualsTest6() {
		Office office = new Office(1, "name", "address");
		Office office2 = new Office(1, "name", "address2");
		Assertions.assertThat(!office.equals(office2));
	}
	
	@Test
	public void officeEqualsTest7() {
		Office office = new Office(1, "name", "address");
		Office office2 = new Office(1, "name2", "address");
		Assertions.assertThat(!office.equals(office2));
	}
}
