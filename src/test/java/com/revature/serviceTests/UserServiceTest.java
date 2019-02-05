package com.revature.serviceTests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.rideforce.user.UserApplication;
import com.revature.rideforce.user.beans.Car;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.exceptions.EmptyPasswordException;
import com.revature.rideforce.user.repository.UserRepository;
import com.revature.rideforce.user.services.UserService;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.assertj.core.api.Assertions;
import org.junit.Assert;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class UserServiceTest {
	@TestConfiguration
	static class UserServiceTestContextConfiguration{
		@Bean
		public UserService userService(UserRepository userRepository) {
			return new UserService(userRepository);
		}
	}
	
	@InjectMocks
	@Autowired
	private UserService userService;
	
	@MockBean
	private UserRepository userRepository;
	
	@Before
	public void setup() {
		User user = new User();
		List<User> list = new ArrayList<>();
		user.setFirstName("john");
		user.setLastName("doe");
		user.setEmail("jdoe@email.com");
		user.setPassword("password");
		list.add(user);
		Mockito.mock(UserRepository.class);
		
	    Mockito.when(userRepository.findAll()).thenReturn(list);
	}
	
//	@Test
//	public void findByEmailTest() throws Exception{
//		User user = new User();
//		User userTest = new User(); 
//		user.setEmail("jdoe@email.com");
//		
//		Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(null);
//
//		
//		Assertions.assertThat(userService.findByEmail("admin@revature.com")).isNotNull();
//	}
}
