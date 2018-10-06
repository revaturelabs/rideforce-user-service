package com.revature.repositoryTests;
 import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.revature.rideforce.user.UserApplication;
import com.revature.rideforce.user.beans.UserRole;
import com.revature.rideforce.user.repository.UserRoleRepository;
 /**
 * holds the JUnit tests for the {@linkplain UserRoleRepository}
 * @author clpeng
 * @since Iteration2 10/22/2018
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
public class UserRoleRepositoryTest {
 	@Autowired
	UserRoleRepository userRoleRepo;
	
	/**
	 * Before running tests, checking class dependencies
	 */
	@Before 
	public void setUp() {
		Assertions.assertThat(userRoleRepo).isNotNull();
	}
	
	/**
	 *  make sure {@linkplain UserRoleRepository#findByTypeIgnoreCase(String) findByTypeIgnoreCase()} works with upper case
	 */
	@Test
	public void findByTypeIgnoreCaseAllUpperCaseTest()
	{
		UserRole userRole = new UserRole();
		userRole.setId(1);
		userRole.setType("ADMIN");
		Assertions.assertThat(userRoleRepo.findByTypeIgnoreCase("ADMIN")).isEqualTo(userRole);
		
	}
	
	/**
	 * make sure {@linkplain UserRoleRepository#findByTypeIgnoreCase(String) findByTypeIgnoreCase()} actually ignores type's case
	 */
	@Test
	public void findByTypeIgnoreCaseDifferentCaseTest()
	{
		UserRole userRole = new UserRole();
		userRole.setId(1);
		userRole.setType("ADMIN");
		Assertions.assertThat(userRoleRepo.findByTypeIgnoreCase("adMin")).isEqualTo(userRole);
		
	}
	
	/**
	 * Happy path test, make sure {@linkplain UserRoleRepository#findById(int) findById()} method works with valid input
	 */
	@Test
	public void findById()
	{
		UserRole userRole = new UserRole();
		userRole.setId(1);
		userRole.setType("ADMIN");
		Assertions.assertThat(userRoleRepo.findById(1)).isEqualTo(userRole);
	}
	/**
	 * Idk, make sure it doesn't throw an exception or something for a bad ID, should just give a null value.
	 * Test for the {@linkplain UserRoleRepository#findById(int) findById()} method
	 */
	@Test
	public void findByIdNonexistentId()
	{
		Assertions.assertThat(userRoleRepo.findById(-1)).isNull();
	}
}