package com.revature.beanTests;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.assertj.core.api.Assertions;
import org.hibernate.validator.HibernateValidator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.revature.rideforce.user.beans.Car;
import com.revature.rideforce.user.beans.ContactInfo;
import com.revature.rideforce.user.beans.Office;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.exceptions.EmptyPasswordException;

public class UserTest {

	private LocalValidatorFactoryBean localValidatorFactory;
	
	private User u;

	@Before
    public void setupValidatorFactory () throws EmptyPasswordException {
        localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
        u = new User();
		u.setId(100);
		u.setFirstName("first");
		u.setLastName("last");
		u.setEmail("j@gmail.com");
		u.setPassword("password");
		u.setPhotoUrl("test.jpg");
		u.setAddress("5125 Ven Ln.");
		u.setBatchEnd(Date.valueOf("2018-11-01"));
		u.setOffice(new Office());
		u.setCars(new HashSet<Car>());
		u.setContactInfo(new HashSet<ContactInfo>());
		u.setVenmo("venmo");
    }
	
	@Test
	public void validUserTest() {
		
		Set<ConstraintViolation<User>> violations = localValidatorFactory.validate(u);
		int counter = 0;
		
		for(ConstraintViolation<User> v: violations) {
			if (!v.getPropertyPath().toString().contains(".")) {
				counter++;
			}
		}
		Assertions.assertThat(counter).isEqualTo(0);
	}
	
	@Test
	public void invalidUserIdTest() {
		u.setId(0);
		Set<ConstraintViolation<User>> violations = localValidatorFactory.validate(u);
		int counter = 0;
		
		for(ConstraintViolation<User> v: violations) {
			if (!v.getPropertyPath().toString().contains(".")) {
				counter++;
			}
		}
		Assertions.assertThat(counter).isEqualTo(1);
	}
	
	@Test
	public void emptyUserFirstNameTest() {
		u.setFirstName("");
		Set<ConstraintViolation<User>> violations = localValidatorFactory.validate(u);
		int counter = 0;
		
		for(ConstraintViolation<User> v: violations) {
			if (!v.getPropertyPath().toString().contains(".")) {
				counter++;
			}
		}
		Assertions.assertThat(counter).isEqualTo(1);
	}
	
	@Test
	public void emptyUserLastNameTest() {
		u.setLastName("");
		Set<ConstraintViolation<User>> violations = localValidatorFactory.validate(u);
		int counter = 0;
		
		for(ConstraintViolation<User> v: violations) {
			if (!v.getPropertyPath().toString().contains(".")) {
				counter++;
			}
		}
		Assertions.assertThat(counter).isEqualTo(1);
	}

	@Test
	public void emptyUserEmailTest() {
		u.setEmail("");
		Set<ConstraintViolation<User>> violations = localValidatorFactory.validate(u);
		int counter = 0;
		
		for(ConstraintViolation<User> v: violations) {
			if (!v.getPropertyPath().toString().contains(".")) {
				counter++;
			}
		}
		Assertions.assertThat(counter).isEqualTo(1);
	}
	
	@Test(expected = EmptyPasswordException.class)
	public void emptyUserPasswordTest() throws EmptyPasswordException {
		u.setPassword("");
//		Set<ConstraintViolation<User>> violations = localValidatorFactory.validate(u);
//		int counter = 0;
//		for(ConstraintViolation<User> v: violations) {
//			if (!v.getPropertyPath().toString().contains(".")) {
//				counter++;
//			}
//		}
//		Assertions.assertThat(counter).isEqualTo(1);
	}
	
	@Test
	public void nullUserRoleTest() {
		u.setRole(null);
		Set<ConstraintViolation<User>> violations = localValidatorFactory.validate(u);
		int counter = 0;
		
		for(ConstraintViolation<User> v: violations) {
			if (!v.getPropertyPath().toString().contains(".")) {
				counter++;
			}
		}
		Assertions.assertThat(counter).isEqualTo(1);
	}
	
	@Test
	public void nullUserOfficeTest() {
		u.setOffice(null);
		Set<ConstraintViolation<User>> violations = localValidatorFactory.validate(u);
		int counter = 0;
		
		for(ConstraintViolation<User> v: violations) {
			if (!v.getPropertyPath().toString().contains(".")) {
				counter++;
			}
		}
		Assertions.assertThat(counter).isEqualTo(1);
	}
	
	@Test
	public void emptyUserAddressTest() {
		u.setAddress("");
		Set<ConstraintViolation<User>> violations = localValidatorFactory.validate(u);
		int counter = 0;
		
		for(ConstraintViolation<User> v: violations) {
			if (!v.getPropertyPath().toString().contains(".")) {
				counter++;
			}
		}
		Assertions.assertThat(counter).isEqualTo(1);
	}
	

	
	@Test
	public void nullUserCarsTest() {
		u.setCars(null);
		Set<ConstraintViolation<User>> violations = localValidatorFactory.validate(u);
		int counter = 0;
		
		for(ConstraintViolation<User> v: violations) {
			if (!v.getPropertyPath().toString().contains(".")) {
				counter++;
			}
		}
		Assertions.assertThat(counter).isEqualTo(1);
	}
	
	@Test
	public void nullUserContactInfoTest() {
		u.setContactInfo(null);
		Set<ConstraintViolation<User>> violations = localValidatorFactory.validate(u);
		int counter = 0;
		
		for(ConstraintViolation<User> v: violations) {
			if (!v.getPropertyPath().toString().contains(".")) {
				counter++;
			}
		}
		Assertions.assertThat(counter).isEqualTo(1);
	}
}
