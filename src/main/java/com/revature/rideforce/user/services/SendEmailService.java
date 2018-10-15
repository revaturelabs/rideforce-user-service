package com.revature.rideforce.user.services;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * for the LoginRecoveryController for modularizing sending the email
 * @author clpeng
 * @since Iteration 2 10/20/2018
 */
public class SendEmailService {

	final static int GMAIL_SMTP_PORT = 587;
	/**
	 * send an email with a link to the front end's reset password page with a token in the url parameter. Sender email is specified within the method 
	 * instead of in the parameters
	 * @param token <code>String</code> needed for building the password reset link
	 * @param receiver the email to send message to
	 */
	public static void sendLoginRecoveryEmail(String token, String receiver)
	{
		//will need a link..."website.com/[token]" 
//		https://www.javatpoint.com/example-of-sending-html-content-with-email-using-java-mail-api
		//https://kinsta.com/knowledgebase/free-smtp-server/
		//https://myaccount.google.com/lesssecureapps     <----Go here and turn it on so gmail doesnt block this app's access to the account
		String sender = "smtp.gmail.com"; 		
		String username = "rideforce.reset@gmail.com";     //dummy email: birthday - 01/01/1996; gender - rather not say; 
		String password = "revaturecode123";
		String url = "http://whateverthefrontendendpointislol.com";
		
		Properties properties = System.getProperties();  //import java.util for "Properties"
		properties.setProperty("mail.smtp.host", sender);  
		properties.put("mail.smtp.auth", "true");  
		properties.put("mail.smtp.port", GMAIL_SMTP_PORT); //465  	//these last two lines, the port for ttls is 587, and you gotta enable ttls first to use 587
		properties.put("mail.smtp.starttls.enable", true);
		
		Session session = Session.getDefaultInstance(properties,     					//Session and MimeMessage from javax.mail
			    new javax.mail.Authenticator() {  
			     protected PasswordAuthentication getPasswordAuthentication() {  
			      return new PasswordAuthentication(username, password);  
			     }  
		});  
		
		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(sender));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
			
			message.setSubject("Password Reset Link from RevatureRideForce");
			message.setContent("<a href= "+"'"+url+"?token="+token+"'>Click here to reset password</a>", "text/html");
			
			Transport.send(message);
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}