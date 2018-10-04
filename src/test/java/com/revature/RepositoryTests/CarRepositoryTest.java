package com.revature.RepositoryTests;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.rideforce.user.UserApplication;
import com.revature.rideforce.user.beans.Car;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.repository.CarRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
public class CarRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private CarRepository repository;
	
	@Test
	public void findByIdTest() {
		entityManager.persist(new Car(1, new User(), "make", "model", 2018));
		Car car = repository.findById(1);
		Assertions.assertThat(car.getId()).isEqualTo(1);
	}
}
