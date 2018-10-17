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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.rideforce.user.UserApplication;
import com.revature.rideforce.user.beans.UserCredentials;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@AutoConfigureMockMvc
public class LoginControllerTest {

	@Autowired
    private MockMvc mockMvc;
	
	@Test
	public void loggedOutUserCannotGetAccessSecureEndpoint() throws Exception {
		this.mockMvc.perform(get("/login")).andExpect(status().isForbidden());
	}
	
	@Test
	public void invalidUserCredentialCannotPostToLogin() throws Exception {
		this.mockMvc.perform(post("/login")).andExpect(status().is4xxClientError());
	}
	
	@Test
	public void validUserCredentialWithNoUserCannotPostToLogin() throws Exception {
		UserCredentials userCred = new UserCredentials("bob@gmail.com", "password");
		ObjectMapper om = new ObjectMapper();
		String userCredJson = om.writeValueAsString(userCred);
		this.mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(userCredJson)).andExpect(status().isForbidden());
	}
	
	@Test
	public void validUserCredentialDifferentCaseEmailShouldntMatterTest() throws Exception {
		UserCredentials userCred = new UserCredentials("adMIN@revATure.com", "password");
		//turn object into JSON string for the body
		ObjectMapper om = new ObjectMapper();
		String userCredJson = om.writeValueAsString(userCred);
		this.mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(userCredJson)).andExpect(status().is2xxSuccessful()); //expect 200 status code from response
	}
	
}
