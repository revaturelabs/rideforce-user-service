package com.revature.beanTests;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.assertj.core.api.Assertions;
import org.hibernate.validator.HibernateValidator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.beans.UserRegistrationInfo;

public class UserRegistrationInfoTest {

	private LocalValidatorFactoryBean localValidatorFactory;

	private UserRegistrationInfo uri; 
	
	@Before
    public void setupValidatorFactory () {
        localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        localValidatorFactory.afterPropertiesSet();
    }
	
	@Test
	public void allNullUserRegInfo() {
		uri = new UserRegistrationInfo();
		uri.setUser(null);
		Validator validator = localValidatorFactory.getValidator();
		Set<ConstraintViolation<UserRegistrationInfo>> violations = validator.validate(uri);
		Assertions.assertThat(violations.size()).isEqualTo(3);
	}
	
	@Test
	public void emptyPasswordAndKeyUserRegInfo() {
		uri = new UserRegistrationInfo(null, "", "");
		Validator validator = localValidatorFactory.getValidator();
		Set<ConstraintViolation<UserRegistrationInfo>> violations = validator.validate(uri);
		Assertions.assertThat(violations.size()).isEqualTo(3);
	}
	
	@Test
	public void nullPasswordTest() {
		uri = new UserRegistrationInfo(new User(), null, "key");
		Set<ConstraintViolation<UserRegistrationInfo>> violations = localValidatorFactory.validate(uri);
		int counter = 0;
		
		for(ConstraintViolation<UserRegistrationInfo> v: violations) {
			String propertyPath = v.getPropertyPath().toString();
			if (propertyPath.equals("password")) {
				counter++;
			}
		}
		Assertions.assertThat(counter).isEqualTo(1);
	}
	
	@Test
	public void emptyPasswordTest() {
		uri = new UserRegistrationInfo(new User(), "", "key");
		Set<ConstraintViolation<UserRegistrationInfo>> violations = localValidatorFactory.validate(uri);
		int counter = 0;
		
		for(ConstraintViolation<UserRegistrationInfo> v: violations) {
			String propertyPath = v.getPropertyPath().toString();
			if (propertyPath.equals("password")) {
				counter++;
			}
		}
		Assertions.assertThat(counter).isEqualTo(1);
	}

	@Test
	public void nullKeyTest() {
		uri = new UserRegistrationInfo(new User(), "password", null);
		Set<ConstraintViolation<UserRegistrationInfo>> violations = localValidatorFactory.validate(uri);
		int counter = 0;
		
		for(ConstraintViolation<UserRegistrationInfo> v: violations) {
			String propertyPath = v.getPropertyPath().toString();
			if (propertyPath.equals("registrationKey")) {
				counter++;
			} 
		}
		Assertions.assertThat(counter).isEqualTo(1);
	}
	
	@Test
	public void emptyKeyTest() {
		uri = new UserRegistrationInfo(new User(), "password", "");
		Set<ConstraintViolation<UserRegistrationInfo>> violations = localValidatorFactory.validate(uri);
		int counter = 0;
		
		for(ConstraintViolation<UserRegistrationInfo> v: violations) {
			String propertyPath = v.getPropertyPath().toString();
			if (propertyPath.equals("registrationKey")) {
				counter++;
			} 
		}
		Assertions.assertThat(counter).isEqualTo(1);
	}
	
	@Test
	public void toStringWorksTest() {
		User u = new User();
		this.uri = new UserRegistrationInfo(u, "credentialpw", "FakeRegistrationKey");
		Assertions.assertThat(this.uri.toString())
			.isEqualTo("UserRegistrationInfo [user=User [id=0, firstName=null, lastName=null, email=null, password=null, photoUrl=null, bio=null, active=ACTIVE, role=UserRole [id=0, type=null], office=Office [id=0, name=null, address=null], address=null, startTime=9.0, batchEnd=null, cars=[], venmo=null, contactInfo=[]], password=credentialpw]");
	}
	@Test
	public void hashCodeWithFilledValuesWorksTest() {
		User u = new User();
		this.uri = new UserRegistrationInfo(u, "credentialpw", "FakeRegistrationKey");
		UserRegistrationInfo otherUri = this.uri;
		Assertions.assertThat(this.uri.hashCode() == otherUri.hashCode()).isTrue();
	}
	@Test
	public void hashCodeWithNullValuesWorksTest() {
		User u = new User();
		this.uri = new UserRegistrationInfo(u, "credentialpw", "FakeRegistrationKey");
		Assertions.assertThat(this.uri.hashCode() == this.uri.hashCode()).isTrue();
	}
	@Test
	public void equalsWithSameObjectTest() {
		User u = new User();
		this.uri = new UserRegistrationInfo(u, "credentialpw", "FakeRegistrationKey");
		Assertions.assertThat(this.uri.equals(this.uri)).isTrue();
	}
	@Test
	public void equalsWithNullOtherObjectTest() {
		User u = new User();
		this.uri = new UserRegistrationInfo(u, "credentialpw", "FakeRegistrationKey");
		Assertions.assertThat(this.uri.equals(null)).isFalse();
	}
	@Test
	public void equalsWithOtherClassIsFalseTest() {
		User u = new User();
		this.uri = new UserRegistrationInfo(u, "credentialpw", "FakeRegistrationKey");
		Assertions.assertThat(this.uri.equals(new Object())).isFalse();
	}
	@Test
	public void equalsWithNullValuesOnOneSetValuesOnOtherTest() {
		User u = new User();
		this.uri = new UserRegistrationInfo(u, "credentialpw", "FakeRegistrationKey");
		UserRegistrationInfo uri2 = new UserRegistrationInfo();
		Assertions.assertThat(uri2.equals(this.uri)).isFalse();
	}
	@Test
	public void equalsWithDifferentSetValuesOnBothTest() {
		User u = new User();
		this.uri = new UserRegistrationInfo(u, "credentialpw", "FakeRegistrationKey");
		UserRegistrationInfo uri2 = new UserRegistrationInfo(u, "differentCredential", "OtherFakeRegistrationKey");
		Assertions.assertThat(uri2.equals(this.uri)).isFalse();
	}
}
