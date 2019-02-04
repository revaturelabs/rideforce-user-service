package com.revature.rideforce.user.services;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.revature.rideforce.user.config.S3Config;

@Service
public class AmazonClientService {
    @Autowired
    private Logger log;
    @Autowired
    private S3Config sc;
    @Autowired
    private AmazonS3 s3client;
    
    public S3ObjectInputStream getFileByKey(String key) throws IOException {    	
    	return s3client.getObject(sc.getBucketName(), key).getObjectContent();
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
           s3client.putObject(new PutObjectRequest(sc.getBucketName(), fileName, file));
    }
    
    public String uploadFile(MultipartFile multipartFile) {
      String fileName = "";
      try {
          File file = convertMultiPartToFile(multipartFile);
          
          fileName = generateFileName(multipartFile);

          uploadFileTos3bucket(fileName, file);
          file.delete();
          
      } catch (Exception e) {
          log.error(e.getMessage());
      }
      return fileName;
    }
    
    public String deleteFileFromS3Bucket(String fileName) {
       s3client.deleteObject(new DeleteObjectRequest(sc.getBucketName(), fileName));
       return "Successfully deleted";
    }
}
