package com.revature.serviceTests;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.revature.rideforce.user.UserApplication;
import com.revature.rideforce.user.beans.Car;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.exceptions.PermissionDeniedException;
import com.revature.rideforce.user.repository.CarRepository;
import com.revature.rideforce.user.repository.UserRepository;
import com.revature.rideforce.user.services.CarService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class) //need this for the Application Context
@Transactional
public class CarServiceTest {
//	https://www.baeldung.com/spring-boot-testing
	@TestConfiguration                                    //<-- this section is for making sure the service bean isn't considered the same as
    static class CarServiceTestContextConfiguration {    //other beans used for actual application during component-scan
        private CarRepository carRepository;
		@Bean
        public CarService carService(CarRepository carRepository) {
    		this.carRepository = carRepository;
    		return new CarService(carRepository);
        }
    }
 
    @Autowired
    private CarService carService;
    @MockBean
    private UserRepository userRepo;   //not sure if we need this :(
    @MockBean
    private CarRepository carRepository;
    
    @Before
    public void setUpMockUserRepo()
    {
    	User user = new User();
    	user.setId(1);
    	user.setFirstName("Admin");
		user.setLastName("Admin");
		user.setEmail("admin@revature.com");
		user.setAddress("11730 Plaza America Dr. Reston, VA");
		user.setCars(new HashSet<>());
		user.setContactInfo(new HashSet<>());
		user.setActive(true);
		Mockito.when( userRepo.getOne(1) ).thenReturn(user);
    }
    
    @Before
    public void setUp()  //set up the mock repo's behavior
    {
    	User user = userRepo.getOne(1);
    	List<Car> list = new ArrayList<>();
        list.add( new Car(1, user, "make", "model", 2012) );
        list.add( new Car(2, user, "honda", "civic", 2016) );
       
        //Mockito is imported from org.mockito :)
//        Mockito.when( userRepo.getOne(1) ).thenReturn(user);
        Mockito.when( carRepository.findByOwner(user) )
          .thenReturn(list);
    }
    
    @Test 
    @WithMockUser(username = "admin@revature.com", password="password", roles="ADMIN")
    public void findByOwnerTest() throws PermissionDeniedException //throws PermissionDeniedException 
    {
    	User user = userRepo.getOne(1);
		Assertions.assertThat( carService.findByOwner(user) ).hasSize(2);
	
    }
    
}
