package com.revature.rideforce.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.revature.rideforce.user.services.AmazonClientService;

@RestController
@PreAuthorize("hasAnyRole('ROLE_TRAINER','ROLE_ADMIN','ROLE_RIDER', 'ROLE_DRIVER')")
@RequestMapping("/storage")
public class BucketController {
	private AmazonClientService amazonClient;

    @Autowired
    BucketController(AmazonClientService amazonClient) {
        this.amazonClient = amazonClient;
    }


    @PostMapping(value = "/uploadFile", consumes = "application/json")
    public String uploadFile(@RequestParam("file") MultipartFile file){
        return this.amazonClient.uploadFile(file);
    }


    @DeleteMapping("/deleteFile")
    public String deleteFile(@RequestBody String fileUrl) {
        return this.amazonClient.deleteFileFromS3Bucket(fileUrl);
    }

}



