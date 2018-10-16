package com.revature.rideforce.user.controllers;

import java.io.IOException;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.rideforce.user.beans.ResponseError;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.beans.forms.LoginRecoveryProcessForm;
import com.revature.rideforce.user.beans.forms.LoginRecoveryTokenSubject;
import com.revature.rideforce.user.exceptions.EmptyPasswordException;
import com.revature.rideforce.user.exceptions.PermissionDeniedException;
import com.revature.rideforce.user.security.LoginRecoveryTokenProvider;
import com.revature.rideforce.user.services.SendEmailService;
import com.revature.rideforce.user.services.UserService;

@RestController("/recovery")
@CrossOrigin
public class LoginRecoveryController {  							//once tests work, make the token stored in the data base
																	//and add a new service? that uses userRepo under a token
	@Autowired
	private UserService userService;
	
	@Autowired
	private LoginRecoveryTokenProvider loginRecoveryTokenProvider;
	
	@PostMapping(consumes= "application/json", produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<?> postSendEmail(@RequestBody String email) throws PermissionDeniedException
	{    
		//find the user by email and rtn
		User user = userService.findByEmailDuringRecovery(email);
		
		if(user != null)  //if user, send an email
		{
			//need to make a token 
			//don't have to really "remember" the token or save it anywhere. The details of the token lie within its string.
			String token = loginRecoveryTokenProvider.generateToken(user.getId());    
			SendEmailService.sendLoginRecoveryEmail(token, email);
		} //if user != null
		
		return (user == null ? 
				new ResponseError("No user with this email").toResponseEntity(HttpStatus.BAD_REQUEST) : ResponseEntity.ok("Successful")); 
		//will be null if no match. They can say if null that no email was sent and they dont have a record of it
	}
	
	//add a method to authentication service
	//need another controller view for clicking that link (2nd). Should verify the token is still good. Then angular has form
	//then on submit, will need 3rd view to receive form and save new password into db
	/**
	 * 
	 * @param token <code>String</code> representing the token that will be part of the viewpoint url
	 * @return <code>int</code> id of the user account to help recover password
	 * @throws IOException 
	 */
																				//For ANGULAR --> https://stackoverflow.com/questions/45997369/how-to-get-param-from-url-in-angular-4/45998138
	@GetMapping(params="token", produces = "application/json")  				 //email link will take to angular's page with path parameter ? token	
	//made wrapper return cuz can be null
	public ResponseEntity<?> processResetPasswordToken(@RequestParam("token") String token) throws IOException  //angular can make GET request here with the token and we return which 
	{																			//userId account to process
		
		LoginRecoveryTokenSubject subject = this.loginRecoveryTokenProvider.checkTokenForUser(token);        //if userId null, should redirect back to login or p/w reset failed page
		if(subject == null)
			return new ResponseError("Token expired or is invalid").toResponseEntity(HttpStatus.NOT_ACCEPTABLE);
		//get a user from the subject id
		return ResponseEntity.ok((User) userService.findByIdDuringRecovery(subject.getId(), token));
	}
	
	
	
	@PutMapping( produces = "application/json") 				//this handles after the angular form is submitted with new p/w
																				//do the check for non null p/w, confirmed p/w same, to pass us just 1 string
	public User processResetPasswordRequest(@RequestBody LoginRecoveryProcessForm tokenAndNewPassword)
	{
		//angular, make sure they dont pass empty password ""
		LoginRecoveryTokenSubject subject = this.loginRecoveryTokenProvider.checkTokenForUser(tokenAndNewPassword.getToken());
		//if userId null, should redirect back to login or p/w reset failed page
		if(subject == null)
			return null;
		
		try {
			User user = userService.findByIdDuringRecovery(subject.getId(), tokenAndNewPassword.getToken());
			if(user != null) 
			{
				user.setPassword(tokenAndNewPassword.getNewPassword());  //if "" password, will throw then in catch, rtn null w/o saving user
				userService.saveDuringRecovery(user, tokenAndNewPassword.getToken());
			}
			
			return user;                     //if during save() the newPassword makes the User object identical to another
		} catch (EmptyPasswordException e) {     //for findbyid, if no user is "logged in"
			return null;
		} 
	}
	
}
