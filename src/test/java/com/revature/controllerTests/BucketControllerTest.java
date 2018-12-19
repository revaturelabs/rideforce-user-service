package com.revature.controllerTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.revature.configuration.WebConfig;
import com.revature.rideforce.user.UserApplication;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.repository.UserRepository;

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
	
	@Autowired
	private UserRepository userRepository;
	
	private String url;
	
	//https://stackoverflow.com/questions/21800726/using-spring-mvc-test-to-unit-test-multipart-post-request
	@Test
	@Ignore
	public void uploadFileTest() throws Exception {
		User admin = userRepository.findById(1); //unfortunately have to get a user
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(admin, "password", admin.getAuthorities()));
		
		File file1 = new File("./src/test/java/com/revature/controllerTests/taggCat.png");
		FileInputStream fis = new FileInputStream(file1);
		MockMultipartFile file = new MockMultipartFile("file", "taggCat.png", "image/png" , fis);
		
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	    url = mockMvc.perform(MockMvcRequestBuilders.multipart("/storage/uploadFile")
	                       .file(file))
	                   .andExpect(status().is(200))
	                   .andReturn().getResponse().getContentAsString();
	    mockMvc.perform(delete("/storage/deleteFile").content(url))
	    		.andExpect(status().is(200))
	    		.andReturn().getResponse().getContentAsString();
	    
	    SecurityContextHolder.getContext().setAuthentication(null);
	}
	 

}
