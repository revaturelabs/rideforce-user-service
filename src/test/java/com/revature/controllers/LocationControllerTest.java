package com.revature.controllers;

import static org.hamcrest.CoreMatchers.is;
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
public class LocationControllerTest {
	
	@Autowired
    private MockMvc mvc;
	
	@Test
	public void getLocationById() throws Exception {
		mvc.perform(get("/locations/1")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().is(200))
			      .andExpect(content()
			      .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			      .andExpect(jsonPath("$.lid", is(1)))
			      .andExpect(jsonPath("$.latitude", is(39.656755)))
			      .andExpect(jsonPath("$.longitude", is(-79.9283087)));
	}
	
	@Test
	public void getLocations() throws Exception {
		mvc.perform(get("/locations")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().is(200))
			      .andExpect(content()
			      .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			      .andExpect(jsonPath("$[0].lid", is(1)))
			      .andExpect(jsonPath("$[0].latitude", is(39.656755)))
			      .andExpect(jsonPath("$[0].longitude", is(-79.9283087)))
			      .andExpect(jsonPath("$[1].lid", is(2)))
			      .andExpect(jsonPath("$[1].latitude", is(39.631848)))
			      .andExpect(jsonPath("$[1].longitude", is(-79.9554057)))
			      .andExpect(jsonPath("$[2].lid", is(3)))
			      .andExpect(jsonPath("$[2].latitude", is(37.4267861)))
			      .andExpect(jsonPath("$[2].longitude", is(-122.0792542197085)));
	}
	
	@Test
	public void createLocationNull() throws Exception {
		mvc.perform(post("/locations")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ }"))
		.andExpect(status().is(400))
		.andExpect(content().string(""));
	}
	
	@Test
	public void updateLocationNull() throws Exception {
		mvc.perform(put("/locations/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ }"))
		.andExpect(status().is(400))
		.andExpect(content().string(""));
	}
	
	@Test
	public void deleteLocationFalse() throws Exception {
		mvc.perform(delete("/locations/50"))
		.andExpect(status().is(200))
		.andExpect(content().string("false"));
	}

}
