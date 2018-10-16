package com.revature.controllerTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.rideforce.user.UserApplication;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.beans.forms.LoginRecoveryProcessForm;
import com.revature.rideforce.user.exceptions.PermissionDeniedException;
import com.revature.rideforce.user.repository.UserRepository;
import com.revature.rideforce.user.security.LoginRecoveryTokenProvider;
import com.revature.rideforce.user.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@AutoConfigureMockMvc
public class LoginRecoveryControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private UserService userService;
	@Autowired
	private LoginRecoveryTokenProvider loginRecoveryTokenProvider;
	@Autowired
	private UserRepository userRepository;
	
	private ObjectMapper objectMapper;
	private String adminsRecoveryToken;
	private User temp;							//test email: "rideforce.reset@gmail.com", password: "revaturecode123" 
	private static final String TEST_EMAIL = "rideforce.reset@gmail.com"; 			//can replace with the email you have access to
	private static final String NOT_IN_DB_EMAIL = "c@boo.com";
	
	@Before
	public void setUp() throws PermissionDeniedException 
	{
		temp = userRepository.findById(1);   
		temp.setEmail(TEST_EMAIL);
		
		this.adminsRecoveryToken = loginRecoveryTokenProvider.generateToken(1);
		this.objectMapper = new ObjectMapper();
		
		Assertions.assertThat(objectMapper).isNotNull();
		Assertions.assertThat(adminsRecoveryToken).isNotNull();
		Assertions.assertThat(temp).isNotNull();
		Assertions.assertThat(userRepository).isNotNull();
		Assertions.assertThat(userService).isNotNull();
		Assertions.assertThat(loginRecoveryTokenProvider).isNotNull();
		Assertions.assertThat(mockMvc).isNotNull();
	}
	
	@Test
	@Transactional
	public void postSendEmailFoundUserTest() throws Exception
	{
		userRepository.save(this.temp);  //just temporarily making admin's email the test email so it sends to an email we can check
		
		String response = this.mockMvc.perform(post("/recovery").contentType("application/json").content(TEST_EMAIL))
									.andExpect(status().is2xxSuccessful())
									.andReturn().getResponse().getContentAsString();
		Assertions.assertThat(response).isEqualTo("Successful");      //json body contains information different from the User object
																			//because of the json link resolver... so just gonna see if it's not null is all
	}
	
	@Test 											//tbh, no way to check if an email unsuccessfully sent, it's just there's no "admin@revature.com" email
	public void postSendEmailNonexistentEmailShouldReturnNullUserAndNotSendEmailTest() throws Exception
	{
		String jsonResponseBody = this.mockMvc.perform(post("/recovery").contentType("application/json").content(NOT_IN_DB_EMAIL))
									.andExpect(status().isBadRequest())
									.andReturn().getResponse().getContentAsString();
	
		Assertions.assertThat(jsonResponseBody).contains("No user with this email");             //The User returned should be null, not found   
	}
	
	@Test
	public void processResetPasswordTokenGoodTokenTest() throws UnsupportedEncodingException, Exception
	{
		String token = this.adminsRecoveryToken;
		String responseBody = this.mockMvc.perform(get("/recovery?token="+token)).andExpect(status().isOk())
																				 .andReturn().getResponse().getContentAsString();
		Assertions.assertThat(responseBody).contains("1");
	}
	
	
	@Test
	public void processResetPasswordTokenBadTokenTest() throws UnsupportedEncodingException, Exception
	{
		String token = "badtoken";
		String responseBody = this.mockMvc.perform(get("/recovery?token="+token)).andExpect(status().is4xxClientError())
																				 .andReturn().getResponse().getContentAsString();
		Assertions.assertThat(responseBody).contains("Token expired or is invalid");
	}
	
	
	//https://stackoverflow.com/questions/20504399/testing-springs-requestbody-using-spring-mockmvc
	@Transactional
	@Test
	public void processResetPasswordRequestTest() throws UnsupportedEncodingException, Exception
	{
		//to set content of requestbody, gotta turn into json string first
		String requestBody =  this.objectMapper.writeValueAsString( new LoginRecoveryProcessForm(this.adminsRecoveryToken, "password2") );
		
		String responseBody = this.mockMvc.perform(put("/recovery").contentType("application/json").content(requestBody))
											.andExpect(status().isOk())
											.andReturn().getResponse().getContentAsString();
		Assertions.assertThat(responseBody).contains("id", "admin", "lastName"); //vs contains "" would be null response body
	}
	
	@Test
	public void processResetPasswordRequestBadTokenShouldGiveNullTest() throws UnsupportedEncodingException, Exception
	{
		String requestBody =  this.objectMapper.writeValueAsString( new LoginRecoveryProcessForm("badtoken", "password2") );
		String responseBody = this.mockMvc.perform(put("/recovery").contentType("application/json").content(requestBody))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		Assertions.assertThat(responseBody).isEmpty(); //vs contains "" would be null response body
	}
}
