package com.revature.beanTests;

import org.hibernate.validator.HibernateValidator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.revature.rideforce.user.beans.ContactInfo;
import com.revature.rideforce.user.beans.ContactType;
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
	
	@Before
	public void setValidUser() {
		user.setId(1);
		user.setFirstName("bob");
		user.setLastName("bobby");
		user.setEmail("bob@gmail.com");
//		user.setRole();
		
	}

	@Test
	public void testCreationOfAValidContactInfo() {
		ContactInfo contactInfo = new ContactInfo(1, new User(),
				new ContactType(), "info");
		
	}
	
	
}
