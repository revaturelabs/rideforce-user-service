package com.revature.repositoryTests;

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
	public void canFindById() {
		// Issues persisting new user, using existing admin in db
		User returnedUser = repository.findById(1);
		Assertions.assertThat(returnedUser).isNotNull();
	}
	
	@Test
	public void canFindByEmail() {
		User returnedUser = repository.findByEmail("admin@revature.com");
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
	public void canFindByOfficeAndRole() {
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
