package com.revature.beanTests;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.assertj.core.api.Assertions;
import org.hibernate.validator.HibernateValidator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.revature.rideforce.user.beans.PasswordChangeRequest;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.beans.UserRegistrationInfo;

public class PasswordChangeRequestModelTest {

	private LocalValidatorFactoryBean localValidatorFactory;
	private PasswordChangeRequest changeRequest; 
	
	@Before
	public void setupValidatorFactory () {
		localValidatorFactory = new LocalValidatorFactoryBean();
		localValidatorFactory.setProviderClass(HibernateValidator.class);
		localValidatorFactory.afterPropertiesSet();
		
		changeRequest = new PasswordChangeRequest();
		changeRequest.setNewPassword("newPassword");
		changeRequest.setOldPassword("oldPassword");
	}
	
	@Test
	public void validPasswordChangeRequestHasNoViolations() {
		PasswordChangeRequest passChangeReq = new PasswordChangeRequest("password", "newpassword");
		Set<ConstraintViolation<PasswordChangeRequest>> violations = localValidatorFactory.validate(passChangeReq);
		Assertions.assertThat(violations.size()).isEqualTo(0);
	}
	
	@Test
	public void emptyOldPasswordChangeRequestHasViolations() {
		PasswordChangeRequest passChangeReq = new PasswordChangeRequest("", "newpassword");
		Set<ConstraintViolation<PasswordChangeRequest>> violations = localValidatorFactory.validate(passChangeReq);
		Assertions.assertThat(violations.size()).isEqualTo(1);
	}
	
	@Test
	public void emptyNewPasswordChangeRequestHasViolations() {
		PasswordChangeRequest passChangeReq = new PasswordChangeRequest("password", "");
		Set<ConstraintViolation<PasswordChangeRequest>> violations = localValidatorFactory.validate(passChangeReq);
		Assertions.assertThat(violations.size()).isEqualTo(1);
	}
	
	@Test
	public void emptyConstructorWorksTest() {
		Assertions.assertThat(this.changeRequest).isNotNull();
	}
	@Test
	public void gettersTest() {
		Assertions.assertThat(this.changeRequest.getNewPassword()).isEqualTo("newPassword");
		Assertions.assertThat(this.changeRequest.getOldPassword()).isEqualTo("oldPassword");
	}
	@Test
	public void toStringTest() {
		Assertions.assertThat(this.changeRequest.toString()).isEqualTo("PasswordChangeRequest [oldPassword=oldPassword, newPassword=newPassword]");
	}
	
	@Test
	public void hashCodeOnItself() {
		Assertions.assertThat(this.changeRequest.hashCode()).isEqualTo(this.changeRequest.hashCode());
	}
	@Test
	public void hashCodeOnCopy() {
		PasswordChangeRequest changeRequest2 = this.changeRequest;
		Assertions.assertThat(this.changeRequest.hashCode()).isEqualTo(changeRequest2.hashCode());
	}
	
	@Test
	public void equalsWithSameObjectTest() {
		Assertions.assertThat(this.changeRequest.equals(this.changeRequest)).isTrue();
	}
	@Test
	public void equalsWithNullOtherObjectTest() {
		Assertions.assertThat(this.changeRequest.equals(null)).isFalse();
	}
	@Test
	public void equalsWithOtherClassIsFalseTest() {
		Assertions.assertThat(this.changeRequest.equals(new Object())).isFalse();
	}
	@Test
	public void equalsWithNullValuesOnOneSetValuesOnOtherTest() {
		PasswordChangeRequest changeRequest2 = new PasswordChangeRequest();
		Assertions.assertThat(changeRequest2.equals(this.changeRequest)).isFalse();
	}
	@Test
	public void equalsWithDifferentSetValuesOnBothTest() {
		PasswordChangeRequest changeRequest2 = new PasswordChangeRequest();
		changeRequest2.setNewPassword("newwwpas");
		changeRequest2.setOldPassword("ollllddpas");
		Assertions.assertThat(changeRequest2.equals(this.changeRequest)).isFalse();
	}
	
}
