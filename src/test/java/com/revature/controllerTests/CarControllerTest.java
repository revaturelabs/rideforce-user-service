package com.revature.controllerTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.revature.rideforce.user.UserApplication;
import com.revature.rideforce.user.controllers.CarController;

@RunWith(SpringRunner.class)
@WebMvcTest
public class CarControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Test
	public void loggedOutUserCannotAccessSecureEndpoint() throws Exception {
		this.mockMvc.perform(get("/cars")).andExpect(status().isForbidden());
	}
	
}