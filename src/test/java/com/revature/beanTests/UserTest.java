package com.revature.beanTests;

import java.net.URI;
import java.net.URISyntaxException;
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
import com.revature.rideforce.user.beans.UserRole;
import com.revature.rideforce.user.exceptions.EmptyPasswordException;

public class UserTest {

	private LocalValidatorFactoryBean localValidatorFactory;
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
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
		u.setStartTime((float) 9.0);
		
		Assertions.assertThat(u.getFirstName()).isEqualTo("first");
		Assertions.assertThat(u.getLastName()).isEqualTo("last");
		Assertions.assertThat(passwordEncoder.matches("password", u.getPassword())).isTrue();
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
	
	@Test
	public void isEnabledTest() {
		Assertions.assertThat(this.u.isEnabled()).isFalse(); //method should just return false
	}
	
	@Test
	public void equalsWithSameObjectTest() {
		Assertions.assertThat(this.u.equals(this.u)).isTrue();
	}
	@Test
	public void equalsWithNullOtherObjectTest() {
		Assertions.assertThat(this.u.equals(null)).isFalse();
	}
	@Test
	public void equalsWithOtherClassIsFalseTest() {
		Assertions.assertThat(this.u.equals(new Object())).isFalse();
	}
	@Test
	public void equalsWithNullValuesOnOneSetValuesOnOtherTest() {
		User user = new User();
		Assertions.assertThat(user.equals(this.u)).isFalse();
	}
	
	@Test
	public void equalsRtnTrueForEqualUserObjectTest() {
		User user = this.u;
		Assertions.assertThat(user.equals(this.u)).isTrue();
	}
	@Test 
	public void equalsRtnFalseForPartsOfUserThatAreNullObjectTest() {
		User user = new User();
		Assertions.assertThat(user.equals(this.u)).isFalse();
	}
	@Test
	public void equalsRtnFalseForUnequalUserObjectTest() {
		Assertions.assertThat(this.u.equals(new User())).isFalse();
	}
	@Test
	public void equalsRtnFalseForComparisonToNullUserTest() {
		Assertions.assertThat(this.u.equals(null)).isFalse();
	}
	@Test
	public void equalsRtnFalseForDifferentClassesTest() {
		Object o = new Object();
		Assertions.assertThat(this.u.equals(o)).isFalse();
	}
	@Test
	public void hashCodeIsSameForTwoSameUsersTest() {
		Assertions.assertThat(this.u.hashCode() == this.u.hashCode()).isTrue();
	}
	@Test
	public void hashCodeIsSameForCopiedUserAndOriginalUserTest() {
		User user = this.u;
		Assertions.assertThat(this.u.hashCode() == user.hashCode()).isTrue();
	}
	@Test
	public void hashCodeDifferentForUserObjectsWithDifferentValuesTest() {
		Assertions.assertThat(this.u.hashCode() == new User().hashCode()).isFalse();
	}
	@Test
	public void toUriTest() throws URISyntaxException {
		Assertions.assertThat(this.u.toUri()).isEqualTo(new URI("/users/"+this.u.getId()));
	}
	@Test
	public void isCredentialsNonExpiredShouldReturnTrueTest() {
		Assertions.assertThat(this.u.isCredentialsNonExpired()).isTrue();
	}
	@Test 
	public void isAccountNonLockedShouldReturnTrueTest() {
		Assertions.assertThat(this.u.isAccountNonLocked()).isTrue();
	}
	@Test 
	public void isAccountNonExpiredShouldReturnTrueTest() {
		Assertions.assertThat(this.u.isAccountNonExpired()).isTrue();
	}
	@Test 
	public void isNotATrainerShouldReturnFalseTest() {
		this.u.setRole(new UserRole(1, "ADMIN"));
		Assertions.assertThat(this.u.isTrainer()).isFalse();
	}
	@Test
	public void isATrainerShouldReturnTrueTest() {
		this.u.setRole(new UserRole(2, "TRAINER"));
		Assertions.assertThat(this.u.isTrainer()).isTrue();
	}
	
	@Test
	public void settersAndGettersMissed() {
		
	}
	
	
}
