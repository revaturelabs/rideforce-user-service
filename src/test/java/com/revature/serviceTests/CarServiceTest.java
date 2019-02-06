package com.revature.serviceTests;

import java.util.ArrayList;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.revature.rideforce.user.UserApplication;
import com.revature.rideforce.user.beans.Car;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.beans.UserRole;
import com.revature.rideforce.user.exceptions.PermissionDeniedException;
import com.revature.rideforce.user.repository.CarRepository;
import com.revature.rideforce.user.repository.UserRepository;
import com.revature.rideforce.user.services.CarService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class) //need this for the Application Context
@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class CarServiceTest {
//	https://www.baeldung.com/spring-boot-testing
	@TestConfiguration                                    //<-- this section is for making sure the service bean isn't considered the same as
    static class CarServiceTestContextConfiguration {    //other beans used for actual application during component-scan
		@Bean
        public CarService carService(CarRepository carRepository) {
    		return new CarService(carRepository);
        }
    }
 
    @Autowired
    private CarService carService;
    
    @Autowired
    private UserRepository userRepo;
    
    @MockBean
    private CarRepository carRepository;
    
    @Before
    public void setUp()  //set up the mock repo's behavior
    {
    	User user = userRepo.getOne(1);
    	List<Car> list = new ArrayList<>();
        list.add( new Car(1, user, "make", "model", 2012) );
        list.add( new Car(2, user, "honda", "civic", 2016) );

        Mockito.when( carRepository.findByOwner(user) )
          .thenReturn(list);
    }
    
    @Test 
    public void findByOwnerTest() throws PermissionDeniedException
    {
    	User owner = new User();
    	//setting a user as logged in so that the user has permission to see the cars
    	SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(owner, "", owner.getAuthorities()));
    	
		Assertions.assertThat( carService.findByOwner(owner) ).isNotNull();
		
		SecurityContextHolder.getContext().setAuthentication(null);
	 
    }
    
    @Test
    public void canAddTest() {
    	User admin = new User();
    	UserRole role = new UserRole(1, "ADMIN");
    	admin.setRole(role);
    	Assertions.assertThat(carService.canAdd(admin, new Car()));
    }
    
}
