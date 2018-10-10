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
public class UserRoleControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void anyoneCanGetRoles() throws Exception {
		this.mockMvc.perform(get("/roles")).andExpect(status().isOk());
	}
	
	@Test
	public void loggedOutUserCannotGetRolesById() throws Exception {
		this.mockMvc.perform(get("/roles/1")).andExpect(status().isForbidden());
	}
	
	@Test
	public void loggedOutUserCannotPostRole() throws Exception {
		this.mockMvc.perform(post("/roles")).andExpect(status().isForbidden());
	}
	
	@Test
	public void loggedOutUserCannotPostRoleById() throws Exception {
		this.mockMvc.perform(post("/roles/1")).andExpect(status().isForbidden());
	}
	
	@Test
	public void loggedOutUserCannotPutRole() throws Exception {
		this.mockMvc.perform(put("/roles")).andExpect(status().isForbidden());
	}
	
	@Test
	public void loggedOutUserCannotPutRoleById() throws Exception {
		this.mockMvc.perform(put("/roles/1")).andExpect(status().isForbidden());
	}
	
	@Test
	public void loggedOutUserCannotDeleteRole() throws Exception {
		this.mockMvc.perform(delete("/roles")).andExpect(status().isForbidden());
	}
	
	@Test
	public void loggedOutUserCannotDeleteRoleById() throws Exception {
		this.mockMvc.perform(delete("/roles/1")).andExpect(status().isForbidden());
	}
	
}
