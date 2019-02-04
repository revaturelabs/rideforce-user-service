package com.revature.rideforce.user.controllers;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.exceptions.EntityConflictException;
import com.revature.rideforce.user.exceptions.PermissionDeniedException;
import com.revature.rideforce.user.services.AmazonClientService;
import com.revature.rideforce.user.services.UserService;
@CrossOrigin(origins="*")
@RestController
//@PreAuthorize("hasAnyRole('ROLE_TRAINER','ROLE_ADMIN','ROLE_RIDER', 'ROLE_DRIVER')")
@RequestMapping("/storage")
public class BucketController {
	@Autowired
    private AmazonClientService amazonClient;
    @Autowired
    UserService userService;
    
    @Autowired
    BucketController(AmazonClientService amazonClient) {
        this.amazonClient = amazonClient;
    }
    
    @GetMapping(value="/getFile/{id}")
    public ResponseEntity<InputStreamResource> getFile(@PathVariable("id") int id) throws PermissionDeniedException, IOException {
    	
    	return prepareFilesDownload(userService.findById(id).getPhotoUrl());
    }    
    
    @PostMapping(value="/uploadFile")
    public void uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("user") int id) throws PermissionDeniedException, EntityConflictException{
        User user = (User) userService.findById(id);
        String url = this.amazonClient.uploadFile(file);
        System.out.println("File uploaded to S3 with url: "+url); //Check for new files with this URL
        user.setPhotoUrl(url);
        userService.save(user);
    }
    
    @DeleteMapping("/deleteFile")
    public String deleteFile(@RequestBody String key) {
        return this.amazonClient.deleteFileFromS3Bucket(key);
    }
    
    private ResponseEntity<InputStreamResource> prepareFilesDownload(String key) throws IOException {

	    HttpHeaders headers = new HttpHeaders();
	    InputStream resp = amazonClient.getFileByKey(key);
	    
	    headers.setContentDispositionFormData("attachment",key);
		return new ResponseEntity<InputStreamResource>(new InputStreamResource(resp), headers, HttpStatus.OK);
    }
    
}
