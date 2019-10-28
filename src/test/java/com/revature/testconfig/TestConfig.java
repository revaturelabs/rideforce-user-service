package com.revature.testconfig;

import java.util.ArrayList;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.google.maps.GeoApiContext;
import com.revature.models.Location;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.repos.LocationRepository;
import com.revature.repos.RoleRepo;
import com.revature.repos.UserRepo;

//@ComponentScan("com.revature")
//@EnableJpaRepositories("com.revature.repos")
//@EntityScan("com.revature.models")
public class TestConfig implements InitializingBean {
	
	@Autowired
	RoleRepo rr;
	
	@Autowired
	UserRepo ur;
	
	@Autowired
	LocationRepository lr;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Role driver = new Role("Driver");
		Role rider = new Role("Rider");
		rr.save(driver);
		rr.save(rider);
		
		Location location1 = new Location("500 Koehler Dr", "Morgantown", "WV", "26505", 39.656755, -79.9283087);
		Location location2 = new Location("496 High St", "Morgantown", "WV", "26505", 39.631848, -79.9554057);
		Location location3 = new Location("1600 Amphitheatre Parkway", "Mountain View", "CA", "94043", 37.4267861, -122.0792542197085);
		lr.save(location1);
		lr.save(location2);
		lr.save(location3);
		
		User user1 = new User("test1@revature.com", "pass", "TestFirst1", "TestLast1", new ArrayList<Role>(), location1, false);
		user1.getRoles().add(rider);
		ur.save(user1);
		User user2 = new User("test2@revature.com", "pass", "TestFirst2", "TestLast2", new ArrayList<Role>(), location2, true);
		user2.getRoles().add(rider);
		user2.getRoles().add(driver);
		ur.save(user2);
		User user3 = new User("test3@revature.com", "pass", "TestFirst3", "TestLast3", new ArrayList<Role>(), location3, false);
		user3.getRoles().add(driver);
		ur.save(user3);
	}

}
