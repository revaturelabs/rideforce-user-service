package com.revature.controllerTests;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.revature.rideforce.user.UserApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@AutoConfigureMockMvc
public class CarControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	private TestUserDetailsService userDetailsService = new TestUserDetailsService();
	
	@Test
	public void loggedOutUserCannotGetAccessSecureEndpoint() throws Exception {
		this.mockMvc.perform(get("/cars")).andExpect(status().isForbidden());
	}
	
	@Test
	public void loggedOutUserCannotGetIdAccessSecureEndpoint() throws Exception {
		this.mockMvc.perform(get("/cars/1")).andExpect(status().isForbidden());
	}
	
	@Test
	public void loggedOutUserCannotPostAccessSecureEndpoint() throws Exception {
		this.mockMvc.perform(post("/cars")).andExpect(status().isForbidden());
	}
	
	@Test
	public void loggedOutUserCannotPutAccessSecureEndpoint() throws Exception {
		this.mockMvc.perform(put("/cars/1")).andExpect(status().isForbidden());
	}
	
	@Test
	public void loggedOutUserCannotDeleteAccessSecureEndpoint() throws Exception {
		this.mockMvc.perform(delete("/cars/1")).andExpect(status().isForbidden());
	}
	
//	@Test
//	public void adminCanAccessSecureEndpoint() throws Exception {
//		UserDetails userDetails= userDetailsService.loadUserByUsername("admin"); 
//		Authentication authentication= new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()) ; 
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//		System.out.println("User Details: " + userDetails);
//		this.mockMvc.perform(get("/cars")).andExpect(status().isOk());
//	}
	
}
