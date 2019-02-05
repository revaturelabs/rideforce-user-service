package com.revature.serviceTests;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.nimbusds.jose.JOSEException;
import com.revature.rideforce.user.UserApplication;
import com.revature.rideforce.user.beans.Office;
import com.revature.rideforce.user.beans.RegistrationToken;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.beans.UserCredentials;
import com.revature.rideforce.user.beans.UserRegistration;
import com.revature.rideforce.user.beans.UserRole;
import com.revature.rideforce.user.exceptions.DisabledAccountException;
import com.revature.rideforce.user.exceptions.EmptyPasswordException;
import com.revature.rideforce.user.exceptions.EntityConflictException;
import com.revature.rideforce.user.exceptions.InvalidCredentialsException;
import com.revature.rideforce.user.exceptions.InvalidRegistrationKeyException;
import com.revature.rideforce.user.exceptions.PasswordRequirementsException;
import com.revature.rideforce.user.exceptions.PermissionDeniedException;
import com.revature.rideforce.user.services.AuthenticationService;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class AuthenticationServiceIntegrationTest {
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@Before
	public void validate() {
		Assertions.assertThat(authenticationService).isNotNull();
	}

	@Test(expected = InvalidRegistrationKeyException.class)
	public void registerWithInvalidRegistrationInfoThrowsException() throws InvalidRegistrationKeyException, EntityConflictException, PermissionDeniedException, 
                                                                    EmptyPasswordException, PasswordRequirementsException, NullPointerException
    {
		//Setting up user register 
		User user = new User();
		user.setFirstName("john");
		user.setLastName("doe");
		user.setEmail("jdoe@gmail.com");
		user.setPassword("password");
		UserRegistration ur = new UserRegistration();
		ur.setUser(user);
		//if no registration token, then it will throw InvalidRegistrationKeyException
		ur.setRegistrationToken(null);
		authenticationService.register(ur);
	}
	
	@Test
	public void registerUser() throws InvalidRegistrationKeyException, EntityConflictException, PermissionDeniedException, EmptyPasswordException, PasswordRequirementsException, JOSEException {
		//Setting up user register 
		User user = new User();
		RegistrationToken rt = new RegistrationToken(); 
		Office office = new Office();
		UserRegistration ur = new UserRegistration();
		Date endDate = new Date();
		UserRole role = new UserRole();
		role.setType("DRIVER");
		role.setId(5);
		user.setFirstName("john");
		user.setLastName("doe");
		user.setEmail("thisisatestemail123413@gmail.com");
		user.setPassword("Password!23");
		user.setRole(role);
		ur.setUser(user);
		office.setName("Reston");
		office.setAddress("123 Main Street");
		rt.setOffice(office);	
		rt.setBatchEndDate(endDate);
		
		ur.setRegistrationToken(authenticationService.createRegistrationToken(rt)); 
		
		authenticationService.register(ur);
	}
	
	@Test
	public void noUserInContext_WillReturnNull() {
		Assertions.assertThat(authenticationService.getCurrentUser()).isNull();
	}
}
