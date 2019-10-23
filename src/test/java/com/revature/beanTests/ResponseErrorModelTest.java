package com.revature.beanTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.assertj.core.api.Assertions;
import org.hibernate.validator.HibernateValidator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.revature.rideforce.user.beans.ResponseError;

@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class ResponseErrorModelTest {

private LocalValidatorFactoryBean localValidatorFactory;
	
	@Before
	public void setupValidatorFactory () {
		localValidatorFactory = new LocalValidatorFactoryBean();
		localValidatorFactory.setProviderClass(HibernateValidator.class);
		localValidatorFactory.afterPropertiesSet();
	}
	
	@Test
	public void validResponseErrorHasNoVilations() {
		ResponseError re = new ResponseError("message");
		re.setMessage("message");
		re.setDetails(new String[]{" ", "hi"});
		Set<ConstraintViolation<ResponseError>> violations = localValidatorFactory.validate(re);
		
		Assertions.assertThat(violations.size()).isEqualTo(0);
	}
	
	@Test
	public void emptyMessageHasViolations() {
		ResponseError re = new ResponseError("message");
		re.setMessage("");
		re.setDetails(new String[]{" ", "hi"});
		
		Set<ConstraintViolation<ResponseError>> violations = localValidatorFactory.validate(re);
		
		Assertions.assertThat(violations.size()).isEqualTo(1);
	}
	
	@Test
	public void nullDetailsHasViolations() {
		ResponseError re = new ResponseError("message");
		re.setMessage("message");
		re.setDetails(null);
		
		Set<ConstraintViolation<ResponseError>> violations = localValidatorFactory.validate(re);
		
		Assertions.assertThat(violations.size()).isEqualTo(1);
	}
	
	@Test
	public void responseErrorHashCodeTest() {
		ResponseError re = new ResponseError("message");
		assertEquals( 954926055, re.hashCode());
	}
	
	@Test
	public void responseErrorEqualsTest1() {
		ResponseError re = new ResponseError("message");
		ResponseError re2 = new ResponseError("message2");
		assertThat(!re.equals(re2));
	}
	
	@Test
	public void responseErrorEqualsTest2() {
		ResponseError re = new ResponseError("message");
		ResponseError re2 = new ResponseError("message");
		assertThat(re.equals(re2));
	}
	
	@Test
	public void responseErrorEqualsTest3() {
		ResponseError re = new ResponseError("message");
		ResponseError re2 = new ResponseError("message");
		String[] details = new String[2];
		re.withDetails(details);
		re.setMessage(null);
		assertThat(!re.equals(re2));
	}
	
	@Test 
	public void toStringTest() {
		ResponseError re = new ResponseError("message");
		assertThat(re.toString().equals("ResponseError [message=message, details=]"));
	}
}

