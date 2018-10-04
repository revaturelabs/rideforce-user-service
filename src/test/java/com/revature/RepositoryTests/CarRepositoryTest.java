package com.revature.RepositoryTests;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
//@DataJpaTest
//@AutoConfigureTestDatabase
public class CarRepositoryTest {

	@Autowired
	private EntityManagerFactory emf;
	
	private EntityManager tem;
	
	@Autowired
	CarRepository carRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Transactional
	@Test
	public void findByIdTest() {
//		tem = emf.createEntityManager();
//		tem.getTransaction().begin();
//		User u = userRepo.findById(1);
//		if (u == null) { 
//			System.out.println("u is null");
//		} else {
//			System.out.println("u is not null");
//		}
////		Car c = new Car(1, u, "make", "model", 2018);
//		Car c = new Car();
//		c.setMake("make");
//		c.setModel("model");
//		c.setOwner(u);
//		c.setYear(2018);
//		tem.persist(c);
//		tem.flush();
		Car car = carRepo.findById(2);
		Assertions.assertThat(car.getId()).isEqualTo(2);
	}
	
//	@Test
//	public void myTest() {
//		User u = userRepo.findById(1);
//		Car c = new Car(1, u, "make", "model", 2018);
//		System.out.println(c);
//		Assertions.assertThat(carRepo.save(c)).isEqualTo(c);
//		Assertions.assertThat(carRepo.findById(1)).isEqualTo(c);
//		Assertions.assertThat(1).isEqualTo(2);
//	}
}
