package com.revature.beanTests;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.assertj.core.api.Assertions;
import org.hibernate.validator.HibernateValidator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.revature.rideforce.user.beans.ContactInfo;
import com.revature.rideforce.user.beans.ContactType;
import com.revature.rideforce.user.beans.User;

public class ContactInfoTest {
	
	private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setupValidatorFactory () {
		localValidatorFactory = new LocalValidatorFactoryBean();
		localValidatorFactory.setProviderClass(HibernateValidator.class);
		localValidatorFactory.afterPropertiesSet();
	}

	@Test
	public void testCreationOfAValidContactInfo() {
		ContactInfo contactInfo = new ContactInfo();
		contactInfo.setId(1);
		contactInfo.setInfo("info");
		contactInfo.setType(new ContactType());
		contactInfo.setUser(new User());
		Set<ConstraintViolation<ContactInfo>> violations = localValidatorFactory.validate(contactInfo);
		int counter = 0;
		
		for(ConstraintViolation<ContactInfo> v : violations) {
			String propertyPath = v.getPropertyPath().toString();
			
			if(propertyPath.contains("contactInfo.")) {
				counter++;
			}
		}
		

		Assertions.assertThat(counter).isEqualTo(0);
	}
	
	@Test
	public void testViolationWithInvalidIdOnContactInfo() {
		ContactInfo contactInfo = new ContactInfo();
		contactInfo.setId(0);
		contactInfo.setUser(new User());
		contactInfo.setType(new ContactType());
		contactInfo.setInfo("info");
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
		ContactInfo contactInfo = new ContactInfo();
		contactInfo.setId(0);
		contactInfo.setUser(null);
		contactInfo.setType(new ContactType());
		contactInfo.setInfo("info");
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
		ContactInfo contactInfo = new ContactInfo();
		contactInfo.setId(0);
		contactInfo.setUser(new User());
		contactInfo.setType(null);
		contactInfo.setInfo("info");
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
		ContactInfo contactInfo = new ContactInfo();
		contactInfo.setId(0);
		contactInfo.setUser(new User());
		contactInfo.setType(new ContactType());
		contactInfo.setInfo("");
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
