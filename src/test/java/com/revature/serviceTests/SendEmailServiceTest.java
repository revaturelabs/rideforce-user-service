package com.revature.serviceTests;

import org.junit.Test;
import com.revature.rideforce.user.services.SendEmailService;

public class SendEmailServiceTest {
	@Test
	public void sendLoginRecoveryEmailWorksTest()  
													//this is not really a test, just wanted to have a place where I can run the method
	{ 												//then I just check my email
		SendEmailService.sendLoginRecoveryEmail("token_here", "rideshare.reset@gmail.com");
															//credentials are "revaturecode123" for this email
	}
}
