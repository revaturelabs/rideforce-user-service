package com.revature.jsonTest;

import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.rideforce.user.UserApplication;
import com.revature.rideforce.user.beans.Car;
import com.revature.rideforce.user.json.CarLinkResolver;
import com.revature.rideforce.user.repository.CarRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class CarLinkResolverTest {

	
	@Autowired
	private CarLinkResolver carLinkResolver;
	
	@MockBean
	private CarRepository carRepository;
	
	@Before
	public void validate() {
		Assertions.assertThat(carLinkResolver).isNotNull();
		
		Car car = new Car();
		car.setId(878);
		car.setMake("honda");
		car.setModel("accord");
		when(carRepository.findById(878)).thenReturn(car);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidLinkThrowsException() {
		String invalidUrl = "/cars/bobby";
		carLinkResolver.resolve(invalidUrl);
	}
	
	@Test
	public void validLinkReturnsCar() {
		String url = "/cars/878";
		Car car = carLinkResolver.resolve(url);
		Assertions.assertThat(carLinkResolver.resolve(url))
			.isNotNull();
		Assertions.assertThat(car.getMake()).isEqualTo("honda");
		Assertions.assertThat(car.getModel()).isEqualTo("accord");
	}
	
	
}
