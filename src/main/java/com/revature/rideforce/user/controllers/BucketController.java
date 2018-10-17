package com.revature.rideforce.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.revature.rideforce.user.services.AmazonClientService;

@RestController
@RequestMapping("/storage")
public class BucketController {
	private AmazonClientService amazonClient;

    @Autowired
    BucketController(AmazonClientService amazonClient) {
        this.amazonClient = amazonClient;
    }


    @PostMapping("/uploadFile")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file){
        return this.amazonClient.uploadFile(file);
    }


    @DeleteMapping("/deleteFile")
    public String deleteFile(@RequestBody String fileUrl) {
        return this.amazonClient.deleteFileFromS3Bucket(fileUrl);
    }

}



