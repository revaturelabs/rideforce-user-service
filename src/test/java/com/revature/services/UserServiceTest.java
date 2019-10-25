package com.revature.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.revature.models.Location;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.repos.UserRepo;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@Mock
	private UserRepo ur;

	@InjectMocks
	private UserServiceImpl us;

	private static User user1;
	private static User user2;
	private static Location location;
	private static List<Role> roles;

	@BeforeClass
	public static void setUp() {
		location = new Location(1, "Test address", "Test city", "Test state", "Test zip", 0.0, 0.0);
		Role actualRole = new Role(1, "Test Role");
		roles = new ArrayList<Role>(Arrays.asList(actualRole));
		user1 = new User(1, "example@revature.com", "password", "Test fname", "Test fname", roles, location, true);
		user2 = new User(1, "sample@revature.com", "password", "Test fname", "Test fname", roles, location, true);
	}

	@Test
	public void contexLoads() throws Exception {
		assertThat(us).isNotNull();
	}

	@Test
	public void getUserById() {
		doReturn(Optional.of(user1)).when(ur).findById(1);

		User testUser = us.getUserById(1);

		assertEquals(testUser, user1);
	}

	@Test
	public void getUserByEmail() {
		doReturn(user1).when(ur).findByEmail("example@revature.com");

		User testUser = us.getUserByEmail("example@revature.com");

		assertEquals(testUser, user1);
	}

	@Test
	public void getAllUsers() {
		Iterable<User> actualUsers = new ArrayList<User>(Arrays.asList(user1, user2));

		doReturn(actualUsers).when(ur).findAll();

		List<User> testUsers = us.getAllUsers();

		assertEquals(testUsers, actualUsers);
	}

	@Test
	public void createUser() {
		User newUser = new User("example@revature.com", "password", "Test fname", "Test fname", roles, location, true);

		doReturn(user1).when(ur).save(newUser);

		User testUser = us.createUser(newUser);

		assertEquals(testUser, user1);
	}

	@Test
	public void updateUser() {
		doReturn(user1).when(ur).save(user1);

		User testUser = us.updateUser(user1);

		assertEquals(testUser, user1);
	}

	@Test
	public void deleteUser() {
		doNothing().when(ur).delete(user1);

		assertEquals(true, us.deleteUser(user1));

		doThrow(new IllegalArgumentException()).when(ur).delete(user1);

		assertEquals(false, us.deleteUser(user1));
	}

}
