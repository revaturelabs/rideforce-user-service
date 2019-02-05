package com.revature.serviceTests;

import static org.mockito.ArgumentMatchers.any;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.rideforce.user.UserApplication;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.beans.UserCredentials;
import com.revature.rideforce.user.beans.UserRegistration;
import com.revature.rideforce.user.exceptions.DisabledAccountException;
import com.revature.rideforce.user.exceptions.EmptyPasswordException;
import com.revature.rideforce.user.exceptions.EntityConflictException;
import com.revature.rideforce.user.exceptions.InvalidCredentialsException;
import com.revature.rideforce.user.exceptions.PermissionDeniedException;
import com.revature.rideforce.user.json.Active;
import com.revature.rideforce.user.repository.UserRepository;
import com.revature.rideforce.user.services.AuthenticationService;
import com.revature.rideforce.user.services.UserService;

@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
@RunWith(SpringRunner.class)   //springRunner class is part of spring, and it's in junit4 folder...so I guess a class set asside for Junit tests
								//RunWith annotation is from JUnit, and I guess it accepts that SpringRunner.class from spring
@SpringBootTest(classes=UserApplication.class)   //this annotation comes from spring boot starter test context.....Application Context
public class AuthenticationServiceUnitTest {
	private User user;
	private UserCredentials userCredentials;
	private UserRegistration registrationInfo;
	@Autowired
	private AuthenticationService authenticationService;
	@MockBean
	private PasswordEncoder passwordEncoder;
	@MockBean
	private UserRepository userRepo;
	@MockBean
	private UserService userService;
	
	@Before
	public void setUpMockitos() throws EntityConflictException, PermissionDeniedException, EmptyPasswordException
	{
		Assertions.assertThat(authenticationService).isNotNull();
		Assertions.assertThat(userRepo).isNotNull();
		this.userCredentials = new UserCredentials();
		Assertions.assertThat(userCredentials).isNotNull();
		this.user = new User();
		user.setId(1);
		user.setLastName("admin");
		user.setFirstName("admin");
		Assertions.assertThat(user).isNotNull();
		Assertions.assertThat(passwordEncoder).isNotNull();
		Assertions.assertThat(userService).isNotNull();
		user.setEmail("admin@revature.com");
		user.setPassword("password");
		user.setActive(Active.ACTIVE);
		Mockito.when(userRepo.findByEmail("admin@revature.com")).thenReturn(user);
		
		Mockito.when( userService.add(any()) ).then( i -> i.getArgument(0) ); //return the user it was given
//		https://stackoverflow.com/questions/2684630/how-can-i-make-a-method-return-an-argument-that-was-passed-to-it
		
	}

	@Test
	public void getCurrentUserTest() throws EmptyPasswordException
	{
		//when no session, getCurrentUser should return null
		Assertions.assertThat(authenticationService.getCurrentUser()).isNull();
		//now set session and should not be null anymore
		user.setPassword("password");
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, "password", user.getAuthorities()));
		Assertions.assertThat(authenticationService.getCurrentUser()).isInstanceOf(User.class).isNotNull().hasFieldOrPropertyWithValue("id", 1);
		SecurityContextHolder.getContext().setAuthentication(null);
	}
}
