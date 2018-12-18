package com.revature.beanTests;

import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.assertj.core.api.Assertions;
import org.hibernate.validator.HibernateValidator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.revature.rideforce.user.beans.ContactType;

@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class ContactTypeTest {
private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setupValidatorFactory () {
		localValidatorFactory = new LocalValidatorFactoryBean();
		localValidatorFactory.setProviderClass(HibernateValidator.class);
		localValidatorFactory.afterPropertiesSet();
	}
	
	@Test
	public void validToString() {
		ContactType contactType = new ContactType(9, "Venmo");
		String output = "ContactType [id=9, type=Venmo]";
		assertTrue(output.equals(contactType.toString()));
	}
	
	@Test
	public void toEnumStringTest() {
		ContactType ct = new ContactType(9, "Venmo");
		assertTrue(ct.toEnumString().equals("VENMO"));
	}
	
	@Test 
	public void hashCodeTest() {
		ContactType ct = new ContactType(9, "Venmo");
		assertEquals(ct.hashCode(), 82542137);
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
	
	@Test 
	public void equalsTest1() {
		ContactType ct = new ContactType(9, "Venmo");
		ContactType ct2 = new ContactType(8, "Other");
		assertThat(!ct.equals(ct2));
	}
	
	@Test
	public void equalsTest2() {
		ContactType ct = new ContactType(9, "Venmo");
		ContactType ct2 = new ContactType(9, "Venmo");
		assertThat(ct.equals(ct2));
	}
	
	@Test
	public void equalsTest3() {
		ContactType ct = new ContactType(9, "Venmo");
		ContactType ct2 = new ContactType(7, "Venmo");
		assertThat(!ct.equals(ct2));
	}
	
	@Test
	public void equalsTest4() {
		ContactType ct = new ContactType(9, "Venmo");
		ContactType ct2 = new ContactType(9, "Other");
		assertThat(!ct.equals(ct2));
	}
}
