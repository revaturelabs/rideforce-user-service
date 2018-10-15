package com.revature.serviceTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.rideforce.user.UserApplication;
import com.revature.rideforce.user.security.LoginRecoveryTokenProvider;
import com.revature.rideforce.user.services.SendEmailService;

public class SendEmailServiceTest {
	@Test
	public void sendLoginRecoveryEmailWorksTest()  
													//this is not really a test, just wanted to have a place where I can run the method
	{ 												//then I just check my email
		SendEmailService.sendLoginRecoveryEmail("token_here", "clpeng@ucdavis.edu");
	}
}
