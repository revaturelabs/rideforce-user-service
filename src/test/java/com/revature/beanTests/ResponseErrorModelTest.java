package com.revature.beanTests;

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
	
}

