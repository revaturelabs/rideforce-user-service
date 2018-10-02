package com.revature.beanTests;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.revature.rideforce.user.beans.UserCredentials;



public class UserCredentialsTest {
	
	UserCredentials uc;
	
	@Test
	public void userCredentialConstructorTest() {
		uc = new UserCredentials();
		Assertions.assertThat(uc.getEmail()).isEqualTo(null);
		Assertions.assertThat(uc.getPassword()).isEqualTo(null);
	}
	
	@Test
	public void userCredentialConstructorParamTest() {
		uc = new UserCredentials("email@gmail.com", "password");
		Assertions.assertThat(uc.getEmail()).isEqualTo("email@gmail.com");
		Assertions.assertThat(uc.getPassword()).isEqualTo("password");
	}
}