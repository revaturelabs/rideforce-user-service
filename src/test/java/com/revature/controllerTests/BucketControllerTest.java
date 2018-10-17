package com.revature.controllerTests;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;

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
	private String url;
	
	//https://stackoverflow.com/questions/21800726/using-spring-mvc-test-to-unit-test-multipart-post-request
	@Test
	public void uploadFileTest() throws Exception {
//		final byte[] imageData = Files.readAllBytes(new File("C:\\Program Files\\Git\\Documents\\1808-Aug13-Java\\p3-rideforce\\rideshare-user-service\\src\\test\\java\\com\\revature\\controllerTests\\very_sad_cat.png").toPath());
		File file1 = new File("./src/test/java/com/revature/controllerTests/taggCat.png");
		FileInputStream fis = new FileInputStream(file1);
		MockMultipartFile file = new MockMultipartFile("file", "taggCat.png", "image/png" , fis);
		
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	    url = mockMvc.perform(MockMvcRequestBuilders.multipart("/storage/uploadFile")
	                       .file(file))
	                   .andExpect(status().is(200))
	                   .andReturn().getResponse().getContentAsString();
	    System.out.println("\n\n\n"+url);
	}
	 
	@Test
	public void printUrl() {
		System.out.println("\n\n\n" + url);
	}
}
