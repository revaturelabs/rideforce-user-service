package com.revature.rideforce.user.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class AmazonClientService {
	
	static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private AmazonS3 s3client;

    @Value("http://rideshare-photos.s3-website-us-east-1.amazonaws.com")
    private String endpointUrl;

    @Value("rideshare-photos")
    private String bucketName;

    @Value("${ACCESS}")
    private String accessKey;

    @Value("${SECRET}")
    private String secretKey;

    @PostConstruct
    private void initializeAmazon() {
       AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
       this.s3client = new AmazonS3Client(credentials);
    }
    
    /**
     * Rename the file with the timestamp so we can upload the same file multiple times
     * @param multiPart
     * @return
     */
    private String generateFileName(MultipartFile multiPart) {
    	return new Date().getTime() + "-" + multiPart.getOriginalFilename().replaceAll(" ",  "_");
    }
    
    private File convertMultiPartToFile(MultipartFile file) throws IOException{

    	File convFile = new File(file.getOriginalFilename());
    	try(FileOutputStream fos = new FileOutputStream(convFile)) {
    		fos.write(file.getBytes());
    	} finally {
    		
    	}
    	
    	
    	return convFile;
    }
    
    private void uploadFileTos3bucket(String fileName, File file) {
    	   s3client.putObject(new PutObjectRequest(bucketName, fileName, file));
	}
    
    public String uploadFile(MultipartFile multipartFile) {
      String fileUrl = "";
      try {
    	  File file = convertMultiPartToFile(multipartFile);
    	  String fileName = generateFileName(multipartFile);
    	  fileUrl = endpointUrl + "/" + fileName;
    	  uploadFileTos3bucket(fileName, file);
    	  file.delete();
      } catch (Exception e) {
    	  logger.error(e.getMessage());
      }
      return fileUrl;
    }
    
    public String deleteFileFromS3Bucket(String fileUrl) {
	   String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
	   s3client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
	   return "Successfully deleted";
	}
}


