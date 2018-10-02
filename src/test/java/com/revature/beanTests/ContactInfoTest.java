package com.revature.beanTests;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.assertj.core.api.Assertions;
import org.hibernate.validator.HibernateValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.revature.rideforce.user.beans.Car;
import com.revature.rideforce.user.beans.ContactInfo;
import com.revature.rideforce.user.beans.ContactType;
import com.revature.rideforce.user.beans.Office;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.beans.UserRole;

public class ContactInfoTest {
	
	private LocalValidatorFactoryBean localValidatorFactory;
	private User user;
	
	
	@Before
	public void setupValidatorFactory () {
		localValidatorFactory = new LocalValidatorFactoryBean();
		localValidatorFactory.setProviderClass(HibernateValidator.class);
		localValidatorFactory.afterPropertiesSet();
	}

	@Test
	public void testCreationOfAValidContactInfo() {
		ContactInfo contactInfo = new ContactInfo(1, new User(),
				new ContactType(), "info");
		Set<ConstraintViolation<ContactInfo>> violations = localValidatorFactory.validate(contactInfo);
		int counter = 0;
		
		for(ConstraintViolation<ContactInfo> v : violations) {
			String propertyPath = v.getPropertyPath().toString();
			System.out.println(propertyPath);
			if(propertyPath.contains("contactInfo.")) {
				counter++;
			}
		}
		

		Assertions.assertThat(counter).isEqualTo(0);
	}
	
	@Test
	public void testViolationWithInvalidIdOnContactInfo() {
		ContactInfo contactInfo = new ContactInfo(0, new User(),
				new ContactType(), "info");
		Set<ConstraintViolation<ContactInfo>> violations = localValidatorFactory.validate(contactInfo);
		int counter = 0;
		
		for(ConstraintViolation<ContactInfo> v : violations) {
			String propertyPath = v.getPropertyPath().toString();
			
			if(propertyPath.equals("id")) {
				counter++;
			}
		}
		
		Assertions.assertThat(counter).isEqualTo(1);
	}
	
	@Test
	public void testViolationWithInvalidUser() {
		ContactInfo contactInfo = new ContactInfo(0, null,
				new ContactType(), "info");
		Set<ConstraintViolation<ContactInfo>> violations = localValidatorFactory.validate(contactInfo);
		int counter = 0;
		
		for(ConstraintViolation<ContactInfo> v : violations) {
			String propertyPath = v.getPropertyPath().toString();
			
			if(propertyPath.equals("user")) {
				counter++;
			}
		}
		
		Assertions.assertThat(counter).isEqualTo(1);
	}
	
	@Test
	public void testViolationWithInvalidContactType() {
		ContactInfo contactInfo = new ContactInfo(0, new User(),
				null, "info");
		Set<ConstraintViolation<ContactInfo>> violations = localValidatorFactory.validate(contactInfo);
		int counter = 0;
		
		for(ConstraintViolation<ContactInfo> v : violations) {
			String propertyPath = v.getPropertyPath().toString();
			
			if(propertyPath.equals("type")) {
				counter++;
			}
		}
		
		Assertions.assertThat(counter).isEqualTo(1);
	}
	
	@Test
	public void testViolationWithEmptyInfo() {
		ContactInfo contactInfo = new ContactInfo(0, new User(),
				new ContactType(), "");
		Set<ConstraintViolation<ContactInfo>> violations = localValidatorFactory.validate(contactInfo);
		int counter = 0;
		
		for(ConstraintViolation<ContactInfo> v : violations) {
			String propertyPath = v.getPropertyPath().toString();
			
			if(propertyPath.equals("info")) {
				counter++;
			}
		}
		
		Assertions.assertThat(counter).isEqualTo(1);
	}
	
}
