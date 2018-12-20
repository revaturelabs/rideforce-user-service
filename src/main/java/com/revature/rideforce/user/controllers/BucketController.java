package com.revature.rideforce.user.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
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
//@CrossOrigin(origins="http://localhost:4200")
@RestController
//@PreAuthorize("hasAnyRole('ROLE_TRAINER','ROLE_ADMIN','ROLE_RIDER', 'ROLE_DRIVER')")
@RequestMapping("/storage")
public class BucketController {
    private AmazonClientService amazonClient;
    @Autowired
    UserService userService;
    
    @Autowired
    BucketController(AmazonClientService amazonClient) {
        this.amazonClient = amazonClient;
    }
    @PostMapping(value="/uploadFile")
    public void uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("user") int id) throws PermissionDeniedException, EntityConflictException{
        User user = (User) userService.findById(id);
        String url = this.amazonClient.uploadFile(file);
        user.setPhotoUrl(url);
        userService.save(user);
    }
    @DeleteMapping("/deleteFile")
    public String deleteFile(@RequestBody String fileUrl) {
        return this.amazonClient.deleteFileFromS3Bucket(fileUrl);
    }
}
