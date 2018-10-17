package com.revature.controllerTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.amazonaws.services.s3.AmazonS3;
import com.revature.rideforce.user.UserApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@AutoConfigureMockMvc
public class BucketControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void uploadFileTest() {
//		this.mockMvc.perform(post("/storage/uploadFile").);
		AmazonS3 client = Mockito.mock(AmazonS3.class);
		
//		S3Uploader uploader = new S3Uploader(client);
	}
}
