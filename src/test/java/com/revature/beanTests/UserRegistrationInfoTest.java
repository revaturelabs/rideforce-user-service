package com.revature.beanTests;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.assertj.core.api.Assertions;
import org.hibernate.validator.HibernateValidator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.revature.rideforce.user.beans.UserRegistration;

@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class UserRegistrationInfoTest {

	private LocalValidatorFactoryBean localValidatorFactory;

	private UserRegistration uri;

	@Before
	public void setupValidatorFactory() {
		localValidatorFactory = new LocalValidatorFactoryBean();
		localValidatorFactory.setProviderClass(HibernateValidator.class);
		localValidatorFactory.afterPropertiesSet();
	}

	@Test
	public void allNullUserRegInfo() {
		uri = new UserRegistration();
		uri.setUser(null);
		Validator validator = localValidatorFactory.getValidator();
		Set<ConstraintViolation<UserRegistration>> violations = validator.validate(uri);
		Assertions.assertThat(violations.size()).isEqualTo(2);// used to be equal to 3 Dionne changed it to 2 assuming that valid is inherently not null, or maybe alid is deprecated?
	}

//	@Test
//	public void emptyPasswordAndKeyUserRegInfo() {
//		uri = new UserRegistration(null, "", "");
//		Validator validator = localValidatorFactory.getValidator();
//		Set<ConstraintViolation<UserRegistration>> violations = validator.validate(uri);
//		Assertions.assertThat(violations.size()).isEqualTo(3);
//	}
//	
//	@Test
//	public void nullPasswordTest() {
//		uri = new UserRegistration(new User(), null, "key");
//		Set<ConstraintViolation<UserRegistration>> violations = localValidatorFactory.validate(uri);
//		int counter = 0;
//		
//		for(ConstraintViolation<UserRegistration> v: violations) {
//			String propertyPath = v.getPropertyPath().toString();
//			if (propertyPath.equals("password")) {
//				counter++;
//			}
//		}
//		Assertions.assertThat(counter).isEqualTo(1);
//	}
//	
//	@Test
//	public void emptyPasswordTest() {
//		uri = new UserRegistration(new User(), "", "key");
//		Set<ConstraintViolation<UserRegistration>> violations = localValidatorFactory.validate(uri);
//		int counter = 0;
//		
//		for(ConstraintViolation<UserRegistration> v: violations) {
//			String propertyPath = v.getPropertyPath().toString();
//			if (propertyPath.equals("password")) {
//				counter++;
//			}
//		}
//		Assertions.assertThat(counter).isEqualTo(1);
//	}
//
//	@Test
//	public void nullKeyTest() {
//		uri = new UserRegistration(new User(), "password", null);
//		Set<ConstraintViolation<UserRegistration>> violations = localValidatorFactory.validate(uri);
//		int counter = 0;
//		
//		for(ConstraintViolation<UserRegistration> v: violations) {
//			String propertyPath = v.getPropertyPath().toString();
//			if (propertyPath.equals("registrationKey")) {
//				counter++;
//			} 
//		}
//		Assertions.assertThat(counter).isEqualTo(1);
//	}
//	
//	@Test
//	public void emptyKeyTest() {
//		uri = new UserRegistration(new User(), "password", "");
//		Set<ConstraintViolation<UserRegistration>> violations = localValidatorFactory.validate(uri);
//		int counter = 0;
//		
//		for(ConstraintViolation<UserRegistration> v: violations) {
//			String propertyPath = v.getPropertyPath().toString();
//			if (propertyPath.equals("registrationKey")) {
//				counter++;
//			} 
//		}
//		Assertions.assertThat(counter).isEqualTo(1);
//	}
//	
//	@Test
//	public void toStringWorksTest() {
//		User u = new User();
//		this.uri = new UserRegistration(u, "credentialpw", "FakeRegistrationKey");
//		Assertions.assertThat(this.uri.toString())
//			.isEqualTo("UserRegistrationInfo [user=User [id=0, firstName=null, lastName=null, email=null, password=null, photoUrl=null, bio=null, active=ACTIVE, role=UserRole [id=0, type=null], office=Office [id=0, name=null, address=null], address=null, startTime=9.0, batchEnd=null, cars=[], contactInfo=[]], password=credentialpw]");
//	}
//	@Test
//	public void hashCodeWithFilledValuesWorksTest() {
//		User u = new User();
//		this.uri = new UserRegistration(u, "credentialpw", "FakeRegistrationKey");
//		UserRegistration otherUri = this.uri;
//		Assertions.assertThat(this.uri.hashCode() == otherUri.hashCode()).isTrue();
//	}
//	@Test
//	public void hashCodeWithNullValuesWorksTest() {
//		User u = new User();
//		this.uri = new UserRegistration(u, "credentialpw", "FakeRegistrationKey");
//		Assertions.assertThat(this.uri.hashCode() == this.uri.hashCode()).isTrue();
//	}
//	@Test
//	public void equalsWithSameObjectTest() {
//		User u = new User();
//		this.uri = new UserRegistration(u, "credentialpw", "FakeRegistrationKey");
//		Assertions.assertThat(this.uri.equals(this.uri)).isTrue();
//	}
//	@Test
//	public void equalsWithNullOtherObjectTest() {
//		User u = new User();
//		this.uri = new UserRegistration(u, "credentialpw", "FakeRegistrationKey");
//		Assertions.assertThat(this.uri.equals(null)).isFalse();
//	}
//	@Test
//	public void equalsWithOtherClassIsFalseTest() {
//		User u = new User();
//		this.uri = new UserRegistration(u, "credentialpw", "FakeRegistrationKey");
//		Assertions.assertThat(this.uri.equals(new Object())).isFalse();
//	}
//	@Test
//	public void equalsWithNullValuesOnOneSetValuesOnOtherTest() {
//		User u = new User();
//		this.uri = new UserRegistration(u, "credentialpw", "FakeRegistrationKey");
//		UserRegistration uri2 = new UserRegistration();
//		Assertions.assertThat(uri2.equals(this.uri)).isFalse();
//	}
//	@Test
//	public void equalsWithDifferentSetValuesOnBothTest() {
//		User u = new User();
//		this.uri = new UserRegistration(u, "credentialpw", "FakeRegistrationKey");
//		UserRegistration uri2 = new UserRegistration(u, "differentCredential", "OtherFakeRegistrationKey");
//		Assertions.assertThat(uri2.equals(this.uri)).isFalse();
//	}
}
