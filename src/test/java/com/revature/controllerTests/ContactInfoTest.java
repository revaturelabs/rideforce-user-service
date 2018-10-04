package com.revature.controllerTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.revature.rideforce.user.UserApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@AutoConfigureMockMvc
public class ContactInfoTest {

	@Autowired
    private MockMvc mockMvc;
	
	@Test
	public void loggedOutUserCannotGetAccessSecureEndpoint() throws Exception {
		this.mockMvc.perform(get("/contact-info")).andExpect(status().isForbidden());
	}
	
	@Test
	public void loggedOutUserCannotGetIdAccessSecureEndpoint() throws Exception {
		this.mockMvc.perform(get("/contact-info/1")).andExpect(status().isForbidden());
	}
	
	@Test
	public void loggedOutUserCannotPostAccessSecureEndpoint() throws Exception {
		this.mockMvc.perform(post("/contact-info")).andExpect(status().isForbidden());
	}
	
	@Test
	public void loggedOutUserCannotPutAccessSecureEndpoint() throws Exception {
		this.mockMvc.perform(put("/contact-info/1")).andExpect(status().isForbidden());
	}
	
	@Test
	public void loggedOutUserCannotDeleteAccessSecureEndpoint() throws Exception {
		this.mockMvc.perform(delete("/contact-info/1")).andExpect(status().isForbidden());
	}
	
}
