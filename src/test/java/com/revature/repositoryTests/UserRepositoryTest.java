package com.revature.repositoryTests;

import java.util.HashSet;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.revature.rideforce.user.UserApplication;
import com.revature.rideforce.user.beans.Office;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.beans.UserRole;
import com.revature.rideforce.user.exceptions.EmptyPasswordException;
import com.revature.rideforce.user.repository.OfficeRepository;
import com.revature.rideforce.user.repository.UserRepository;
import com.revature.rideforce.user.repository.UserRoleRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@Transactional
public class UserRepositoryTest {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private UserRoleRepository userRoleRepo;
	
	@Autowired
	private OfficeRepository officeRepo;
	
	private User user;
	
//	@Before
//	public void setup() throws EmptyPasswordException {
//		Assertions.assertThat(repository).isNotNull();
//	}
	
	@Before
	public void validate() {
		Assertions.assertThat(repository).isNotNull();
	}
	
	@Test 
	public void findByIdNegativeId() {
		User returnedUser = repository.findById(-1);
		Assertions.assertThat(returnedUser).isNull();
	}
	
	@Test
	public void findByIdNonexistantId() {
		User returnedUser = repository.findById(9999999);
		Assertions.assertThat(returnedUser).isNull();
	}
	
	@Test
	public void canFindById() throws EmptyPasswordException {
		// Issues persisting new user, using existing admin in db
		user = new User();
		user.setId(400);
		user.setFirstName("first");
		user.setLastName("last");
		user.setEmail("email@email.com");
		user.setPassword("password");
		user.setRole(userRoleRepo.findById(1));
		user.setOffice(officeRepo.findById(1));
		user.setAddress("address");
		user.setStartTime((float) 9.0);
		user.setCars(new HashSet<>());
		user.setContactInfo(new HashSet<>());
		user = repository.save(user);
		User returnedUser = repository.findById(user.getId());
		Assertions.assertThat(returnedUser).isNotNull();
	}
	
	@Test
	public void canFindByEmail() throws EmptyPasswordException {
		user = new User();
		user.setId(400);
		user.setFirstName("first");
		user.setLastName("last");
		user.setEmail("email@email.com");
		user.setPassword("password");
		user.setRole(userRoleRepo.findById(1));
		user.setOffice(officeRepo.findById(1));
		user.setAddress("address");
		user.setStartTime((float) 9.0);
		user.setCars(new HashSet<>());
		user.setContactInfo(new HashSet<>());
		user = repository.save(user);
		User returnedUser = repository.findByEmail("email@email.com");
		Assertions.assertThat(returnedUser).isNotNull();
	}
	
	@Test
	public void findByEmailEmptyString() {
		User returnedUser = repository.findByEmail("");
		Assertions.assertThat(returnedUser).isNull();
	}
	
	@Test
	public void findByEmailNonexistantEmail() {
		User returnedUser = repository.findByEmail("doesn'texist");
		Assertions.assertThat(returnedUser).isNull();
	}
	
	@Test
	public void canFindByOfficeAndRole() throws EmptyPasswordException {
		user = new User();
		user.setId(400);
		user.setFirstName("first");
		user.setLastName("last");
		user.setEmail("email@email.com");
		user.setPassword("password");
		user.setRole(userRoleRepo.findById(1));
		user.setOffice(officeRepo.findById(1));
		user.setAddress("address");
		user.setStartTime((float) 9.0);
		user.setCars(new HashSet<>());
		user.setContactInfo(new HashSet<>());
		user = repository.save(user);
		List<User> returnedUser = repository.findByOfficeAndRole(officeRepo.findById(1), userRoleRepo.findById(1));
		Assertions.assertThat(returnedUser).isNotNull();
	}
	
	@Test(expected = Exception.class)
	public void findByNonexistantOfficeAndRole() {
		repository.findByOfficeAndRole(new Office(), userRoleRepo.findById(1));
	}
	
	@Test(expected = Exception.class)
	public void findByOfficeAndNonexistantRole() {
		repository.findByOfficeAndRole(officeRepo.findById(1), new UserRole());
	}

	@Test
	public void findByNullOfficeAndRole() {
		List<User> returnedUser = repository.findByOfficeAndRole(null, userRoleRepo.findById(1));
		Assertions.assertThat(returnedUser).isEmpty();
	}
	
	@Test
	public void findByOfficeAndNullRole() {
		List<User> returnedUser = repository.findByOfficeAndRole(officeRepo.findById(1), null);
		Assertions.assertThat(returnedUser).isEmpty();
	}
}
