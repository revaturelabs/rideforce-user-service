package com.revature.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.revature.rideforce.RideforceV2UserServiceApplication;
import com.revature.testconfig.TestConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RideforceV2UserServiceApplication.class, webEnvironment=WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = TestConfig.class)
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
    private MockMvc mvc;
	
	@Test
	public void getUserById() throws Exception {
		mvc.perform(get("/users/1")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().is(200))
			      .andExpect(content()
			      .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			      .andExpect(jsonPath("$.uid", is(1)))
			      .andExpect(jsonPath("$.email", is("test1@revature.com")));
	}
	
	@Test
	public void getUsers() throws Exception {
		mvc.perform(get("/users")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().is(200))
			      .andExpect(content()
			      .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			      .andExpect(jsonPath("$[0].uid", is(1)))
			      .andExpect(jsonPath("$[0].email", is("test1@revature.com")))
			      .andExpect(jsonPath("$[1].uid", is(2)))
			      .andExpect(jsonPath("$[1].email", is("test2@revature.com")))
			      .andExpect(jsonPath("$[2].uid", is(3)))
			      .andExpect(jsonPath("$[2].email", is("test3@revature.com")));
	}
	
	@Test
	public void createUserNull() throws Exception {
		mvc.perform(post("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ }"))
		.andExpect(status().is(400))
		.andExpect(content().string(""));
	}
	
	@Test
	public void updateUserNull() throws Exception {
		mvc.perform(put("/users/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ }"))
		.andExpect(status().is(400))
		.andExpect(content().string(""));
	}
	
	@Test
	public void deleteUserFalse() throws Exception {
		mvc.perform(delete("/users/50"))
		.andExpect(status().is(200))
		.andExpect(content().string("false"));
	}

}
