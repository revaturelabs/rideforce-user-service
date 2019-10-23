package com.revature.controllerTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.ws.rs.core.MediaType;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.rideforce.user.UserApplication;
import com.revature.rideforce.user.beans.UserCredentials;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class LoginControllerTest {

	@Autowired
    private MockMvc mockMvc;
	
	@Test
	public void loggedOutUserCannotGetAccessSecureEndpoint() throws Exception {
		this.mockMvc.perform(get("/login")).andExpect(status().isForbidden());
	}
	/////////tThere is no post method for login so the code should all be method not found
	@Test
	public void invalidUserCredentialCannotPostToLogin() throws Exception {
		this.mockMvc.perform(post("/login")).andExpect(status().is4xxClientError());
	}
	
	@Test
	public void validUserCredentialWithNoUserCannotPostToLogin() throws Exception {
		UserCredentials userCred = new UserCredentials("bob@gmail.com", "password");
		ObjectMapper om = new ObjectMapper();
		String userCredJson = om.writeValueAsString(userCred);
		this.mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(userCredJson)).andExpect(status().is4xxClientError());
	}
	
	@Test
	//unsure why this does not work 
	public void validUserCredentialDifferentCaseEmailShouldntMatterTest() throws Exception {
		
		UserCredentials userCred = new UserCredentials("KyleSAnderson@jourrapide.com", "12345678");
		//turn object into JSON string for the body
		ObjectMapper om = new ObjectMapper();
		String userCredJson = om.writeValueAsString(userCred);
		this.mockMvc.perform(get("/login").contentType(MediaType.APPLICATION_JSON).content(userCredJson)).andExpect(status().is2xxSuccessful()); //expect 200 status code from response
	}
	
}
