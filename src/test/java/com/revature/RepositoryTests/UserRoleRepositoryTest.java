package com.revature.RepositoryTests;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.rideforce.user.UserApplication;
import com.revature.rideforce.user.beans.UserRole;
import com.revature.rideforce.user.repository.UserRoleRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@DataJpaTest
public class UserRoleRepositoryTest {
	
	@Autowired
	TestEntityManager testEntityManager;
	
	@Autowired
	UserRoleRepository userRoleRepository;
	
	@Test
	public void userRoleShouldBeEmptyIfNoUserRoles() {
		Iterable<UserRole> roles = userRoleRepository.findAll();
		Assertions.assertThat(roles).isEmpty();
		
	}
}
