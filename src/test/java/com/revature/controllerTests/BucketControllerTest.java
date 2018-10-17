package com.revature.controllerTests;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.revature.configuration.WebConfig;
import com.revature.rideforce.user.UserApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@AutoConfigureMockMvc
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
public class BucketControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	//https://stackoverflow.com/questions/21800726/using-spring-mvc-test-to-unit-test-multipart-post-request
	@Test
	public void uploadFileTest() throws Exception {
		
		MockMultipartFile file = new MockMultipartFile("file", "very_sad_cat.png", "image/png" , "very_sad_cat.png".getBytes());
		
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	       mockMvc.perform(MockMvcRequestBuilders.multipart("/storage/uploadFile")
	                       .file(file))
	                   .andExpect(status().is(200));
	}
}
