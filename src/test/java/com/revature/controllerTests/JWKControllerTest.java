package com.revature.controllerTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.rideforce.user.UserApplication;
import com.revature.rideforce.user.beans.Office;
import com.revature.rideforce.user.beans.RegistrationToken;
import com.sun.jersey.api.client.ClientResponse.Status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class JWKControllerTest {
	
	private RegistrationToken rt = new RegistrationToken();
	private Office office = new Office(); 
	
	@Autowired
	private MockMvc mockMvc; 
	
	@Test
	public void registrationTokenPostTest() throws Exception {
		Date batchEndDate = new Date();
		office.setName("Reston");
		office.setAddress("123 Main St");
		rt.setOffice(office);
		rt.setBatchEndDate(batchEndDate);
		ObjectMapper om = new ObjectMapper(); 
		String tokenInfo = om.writeValueAsString(rt);
		this.mockMvc.perform(post("/tokens/registration").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(tokenInfo)).andExpect(status().isCreated());
	}
	
	@Test
	public void registrationTokenGetTest() throws Exception {
		this.mockMvc.perform(get("/tokens/registration")).andExpect(status().isOk());
	}
}
