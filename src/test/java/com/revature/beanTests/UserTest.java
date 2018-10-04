package com.revature.beanTests;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.assertj.core.api.Assertions;
import org.hibernate.validator.HibernateValidator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.revature.rideforce.user.beans.Car;
import com.revature.rideforce.user.beans.ContactInfo;
import com.revature.rideforce.user.beans.Office;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.beans.UserRole;

public class UserTest {

	private LocalValidatorFactoryBean localValidatorFactory;
	
	private User u;

	@Before
    public void setupValidatorFactory () {
        localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
    }
	
	@Test
	public void validUserTest() {
		u = new User(100, "first", "last", "j@gmail.com", "pass", "test.jpg", "bio", true, new UserRole(), new Office(), "home", new Date(2018), new HashSet<Car>(), "venmo", new HashSet<ContactInfo>());
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
		u = new User(0, "first", "last", "j@gmail.com", "pass", "test.jpg", "bio", true, new UserRole(), new Office(), "home", new Date(2018), new HashSet<Car>(), "venmo", new HashSet<ContactInfo>());
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
		u = new User(100, "", "last", "j@gmail.com", "pass", "test.jpg", "bio", true, new UserRole(), new Office(), "home", new Date(2018), new HashSet<Car>(), "venmo", new HashSet<ContactInfo>());
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
	public void nullUserFirstNameTest() {
		u = new User(100, null, "last", "j@gmail.com", "pass", "test.jpg", "bio", true, new UserRole(), new Office(), "home", new Date(2018), new HashSet<Car>(), "venmo", new HashSet<ContactInfo>());
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
		u = new User(100, "first", "", "j@gmail.com", "pass", "test.jpg", "bio", true, new UserRole(), new Office(), "home", new Date(2018), new HashSet<Car>(), "venmo", new HashSet<ContactInfo>());
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
	public void nullUserLastNameTest() {
		u = new User(100, "first", null, "j@gmail.com", "pass", "test.jpg", "bio", true, new UserRole(), new Office(), "home", new Date(2018), new HashSet<Car>(), "venmo", new HashSet<ContactInfo>());
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
		u = new User(100, "first", "last", "", "pass", "test.jpg", "bio", true, new UserRole(), new Office(), "home", new Date(2018), new HashSet<Car>(), "venmo", new HashSet<ContactInfo>());
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
	public void nullUserEmailTest() {
		u = new User(100, "first", "last", null, "pass", "test.jpg", "bio", true, new UserRole(), new Office(), "home", new Date(2018), new HashSet<Car>(), "venmo", new HashSet<ContactInfo>());
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
	public void emptyUserPasswordTest() {
		u = new User(100, "first", "last", "j@gmail.com", "", "test.jpg", "bio", true, new UserRole(), new Office(), "home", new Date(2018), new HashSet<Car>(), "venmo", new HashSet<ContactInfo>());
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
	public void nullUserPasswordTest() {
		u = new User(100, "first", "last", "j@gmail.com", null, "test.jpg", "bio", true, new UserRole(), new Office(), "home", new Date(2018), new HashSet<Car>(), "venmo", new HashSet<ContactInfo>());
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
	public void nullUserRoleTest() {
		u = new User(100, "first", "last", "j@gmail.com", "password", "test.jpg", "bio", true, null, new Office(), "home", new Date(2018), new HashSet<Car>(), "venmo", new HashSet<ContactInfo>());
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
		u = new User(100, "first", "last", "j@gmail.com", "password", "test.jpg", "bio", true, new UserRole(), null, "home", new Date(2018), new HashSet<Car>(), "venmo", new HashSet<ContactInfo>());
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
		u = new User(100, "first", "last", "j@gmail.com", "password", "test.jpg", "bio", true, new UserRole(), new Office(), "", new Date(2018), new HashSet<Car>(), "venmo", new HashSet<ContactInfo>());
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
	public void nullUserAddressTest() {
		u = new User(100, "first", "last", "j@gmail.com", "password", "test.jpg", "bio", true, new UserRole(), new Office(), null, new Date(2018), new HashSet<Car>(), "venmo", new HashSet<ContactInfo>());
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
		u = new User(100, "first", "last", "j@gmail.com", "pass", "test.jpg", "bio", true, new UserRole(), new Office(), "home", new Date(2018), null, "venmo", new HashSet<ContactInfo>());
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
		u = new User(100, "first", "last", "j@gmail.com", "pass", "test.jpg", "bio", true, new UserRole(), new Office(), "home", new Date(2018), new HashSet<Car>(), "venmo", null);
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
