package com.revature.rideforce.user.services;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.text.SimpleDateFormat;

import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

@Service
public class AmazonClientService {
    
    static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private AmazonS3 s3client;
    /*
    @Value("http://rideshare-photos.s3-website-us-east-1.amazonaws.com")
    private String endpointUrl;
    */
    @Value("us-east-1")
    private String regionName;
    @Value("rideshare-photos")
    private String bucketName;
    @Value("${ACCESS}")
    private String accessKey;
    @Value("${SECRET}")
    private String secretKey;
    @PostConstruct
    private void initializeAmazon() {
       AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
       this.s3client = AmazonS3ClientBuilder
    		   .standard()
    		   .withRegion(Regions.US_EAST_1)
    		   .withCredentials(new AWSStaticCredentialsProvider(credentials))
    		   .build();
    }
    
    public S3ObjectInputStream getFileByKey(String key) throws IOException {    	
    	
    	return s3client.getObject(bucketName, key).getObjectContent();
    }

    /**
     * Rename the file with the timestamp so we can upload the same file multiple times
     * @param multiPart
     * @return
     */
    private String generateFileName(MultipartFile multiPart) {
    	SimpleDateFormat sdfDate = new SimpleDateFormat("EEE_MMM_d_HH:mm:ss_z_y");
    	String strDate = sdfDate.format(System.currentTimeMillis());
    	
    	return strDate.concat(multiPart.getOriginalFilename()).replaceAll(" ",  "_");
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
      String fileName = "";
      try {
          File file = convertMultiPartToFile(multipartFile);
          
          fileName = generateFileName(multipartFile);

          uploadFileTos3bucket(fileName, file);
          file.delete();
          
      } catch (Exception e) {
          logger.error(e.getMessage());
      }
      return fileName;
    }
    
    public String deleteFileFromS3Bucket(String fileName) {
    	
       s3client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
       return "Successfully deleted";
    }
}
