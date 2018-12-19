package com.revature.serviceTests;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.rideforce.user.UserApplication;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.repository.ContactInfoRepository;
import com.revature.rideforce.user.services.ContactInfoService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class ContactInfoServiceTest {

	@TestConfiguration                                    //<-- this section is for making sure the service bean isn't considered the same as
    static class ContactInfoServiceTestContextConfiguration {    //other beans used for actual application during component-scan
		@Bean
        public ContactInfoService contactInfoService(ContactInfoRepository contactInfoRepository) {
    		return new ContactInfoService(contactInfoRepository);
        }
    }
	
	@Autowired
    private ContactInfoService contactInfoService;

    @MockBean
    private ContactInfoRepository contactInfoRepository;
    
    @Before
    public void setUp() {
    	Assertions.assertThat(contactInfoRepository).isNotNull();
    	Assertions.assertThat(contactInfoService).isNotNull();
    }
    
    @Test
    public void contactInfoServiceFindByUserCanFind() {
    	User user = new User();
    	user.setId(1);
    	Assertions.assertThat(contactInfoService.findByUser(user)).isNotNull();
    }
    
    @Test
    public void contactInfoServiceFindByUserReturnsEmptyWithInvalidUser() {
    	User user = new User();
    	user.setId(99990);
    	System.out.println("Finding by user: " + contactInfoService.findByUser(user));
    	Assertions.assertThat(contactInfoService.findByUser(user)).isEmpty();
    }
    
    
	
}
