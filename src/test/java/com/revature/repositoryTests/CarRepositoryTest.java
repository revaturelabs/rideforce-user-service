package com.revature.repositoryTests;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.revature.rideforce.user.UserApplication;
import com.revature.rideforce.user.beans.Car;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.repository.CarRepository;
import com.revature.rideforce.user.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@Transactional
public class CarRepositoryTest {

	@Autowired
	private CarRepository repository;
	@Autowired
	private UserRepository userRepo;
	
	@Test
	public void findByIdTest() 
	{
		//if you want to see the actual item saved, remove "@Transactional" so that it won't rollback :-)
		User user = userRepo.getOne(1);  //for the way the test is set up, to compare to the car object, have to read the user from db :(
		Car c = repository.save(new Car(user, "Honda", "civic", 2018));    //Have to save a new car because the generated ID is a random #
		Assertions.assertThat(repository.findById(c.getId())).isEqualTo(c); //have to grab c's id because it's random so we don't know it
	}
	
	@Test
	public void findByOwnerTest() 
	{
		User user = userRepo.getOne(1);
		Car c = repository.save(new Car(user, "Honda", "civic", 2018));
		Assertions.assertThat(repository.findByOwner(user)).containsAnyOf(c);  //not sure if this is best test because wont know 
																	// how big the list will be, but can at least add to it and see if it's in the
																	//list?
	}
}