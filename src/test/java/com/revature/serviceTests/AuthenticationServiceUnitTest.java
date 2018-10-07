package com.revature.serviceTests;

import static org.hamcrest.CoreMatchers.any;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.rideforce.user.UserApplication;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.beans.UserCredentials;
import com.revature.rideforce.user.beans.UserRegistrationInfo;
import com.revature.rideforce.user.exceptions.EntityConflictException;
import com.revature.rideforce.user.exceptions.InvalidCredentialsException;
import com.revature.rideforce.user.exceptions.InvalidRegistrationKeyException;
import com.revature.rideforce.user.exceptions.PermissionDeniedException;
import com.revature.rideforce.user.repository.UserRepository;
import com.revature.rideforce.user.security.RegistrationTokenProvider;
import com.revature.rideforce.user.services.AuthenticationService;
import com.revature.rideforce.user.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=UserApplication.class)   
public class AuthenticationServiceUnitTest {
	
	private User user;
	private UserCredentials userCredentials;
	private UserRegistrationInfo registrationInfo;
	@Autowired
	private RegistrationTokenProvider registrationTokenProvider;
	@Autowired
	private AuthenticationService authenticationService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@MockBean
	private UserRepository userRepo;
	@MockBean
	private UserService userService;
	
	@Before 
	public void setUp()
	{
		Assertions.assertThat(authenticationService).isNotNull();
		Assertions.assertThat(userRepo).isNotNull();
		this.userCredentials = new UserCredentials();
		Assertions.assertThat(userCredentials).isNotNull();
		this.user = new User();
		user.setId(1);
		Assertions.assertThat(user).isNotNull();
		Assertions.assertThat(passwordEncoder).isNotNull();
		Assertions.assertThat(userService).isNotNull();
	}
	
	@Before
	public void setUpMockitos() throws EntityConflictException, PermissionDeniedException
	{
		User user = new User();
		user.setEmail("admin@revature.com");
		user.setPassword(passwordEncoder.encode("password"));
		Mockito.when(userRepo.findByEmail("admin@revature.com")).thenReturn(user);
		
		Mockito.when( userService.add(any()) ).then( i -> i.getArgument(0) ); //return the user it was given
//		https://stackoverflow.com/questions/2684630/how-can-i-make-a-method-return-an-argument-that-was-passed-to-it
		
	}
	
	/**
	 * Test the logic of the authentication service layer is sound
	 * @throws InvalidCredentialsException 
	 */
	@Test
	public void authenticateWithGoodCredentialsTest() throws InvalidCredentialsException
	{
		userCredentials.setEmail("admin@revature.com");
		userCredentials.setPassword("password"); 									//ultimately the token returned by LoginTokenProvider is just a string
		Assertions.assertThat(authenticationService.authenticate(userCredentials)).isInstanceOf(String.class).isNotNull();
	}
	
	@Test
	public void authenticateWithBadPasswordTest()
	{
		userCredentials.setEmail("admin@revature.com");
		userCredentials.setPassword("pas"); 	
		Assertions.assertThatExceptionOfType(InvalidCredentialsException.class)
			.isThrownBy( () -> authenticationService.authenticate(userCredentials) );  //yay lambdas...w/o doesnt work to just put the method
	}
	
	@Test
	public void authenticateWithBadEmailTest()
	{
		userCredentials.setEmail("bad");
		Throwable thrown = Assertions.catchThrowable( () -> authenticationService.authenticate(userCredentials) );
		Assertions.assertThat(thrown).isInstanceOf(InvalidCredentialsException.class);
	}
	
	@Test
	public void registerTest() throws InvalidRegistrationKeyException, EntityConflictException, PermissionDeniedException
	{
		String token = registrationTokenProvider.generateToken();
		registrationInfo = new UserRegistrationInfo(this.user, "pally", token);
		Assertions.assertThat( authenticationService.register(registrationInfo) ).isInstanceOf(User.class).isNotNull();
	}
	
	@Test
	public void registerWithInvalidRegistrationKey()
	{
		registrationInfo = new UserRegistrationInfo(this.user, "pally", "badkey");
		Throwable caughtException = Assertions.catchThrowable( () -> authenticationService.register(registrationInfo) );
		Assertions.assertThat(caughtException).isInstanceOf(InvalidRegistrationKeyException.class);
	}
	
	@Test
	public void registerWithNullPassword() throws InvalidRegistrationKeyException, EntityConflictException, PermissionDeniedException
	{
		registrationInfo = new UserRegistrationInfo(null, null, registrationTokenProvider.generateToken());
		Throwable caughtException = Assertions.catchThrowable(() -> authenticationService.register(registrationInfo));
		Assertions.assertThat(caughtException).isInstanceOf(NullPointerException.class);
	}
	
	@Test
	public void getCurrentUser()
	{
		
	}
}