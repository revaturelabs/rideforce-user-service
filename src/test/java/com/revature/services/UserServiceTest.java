package com.revature.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import org.springframework.dao.EmptyResultDataAccessException;

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
		Role role = new Role(1, "Test Role");
		roles = new ArrayList<Role>(Arrays.asList(role));
		user1 = new User(1, "example@revature.com", "password", "Test fname", "Test fname", roles, location, true);
		user2 = new User(1, "sample@revature.com", "password", "Test fname", "Test fname", roles, location, true);
	}

	@Test
	public void contexLoads() throws Exception {
		assertNotNull(us);
	}

	@Test
	public void getUserById() {
		doReturn(Optional.of(user1)).when(ur).findById(user1.getUid());

		User testUser = us.getUserById(user1.getUid());

		assertEquals(testUser, user1);
	}

	@Test
	public void getUserByEmail() {
		doReturn(user1).when(ur).findByEmail(user1.getEmail());

		User testUser = us.getUserByEmail(user1.getEmail());

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
	public void createUserValid() {
		User newUser = new User("example@revature.com", "password", "Test fname", "Test fname", roles, location, true);

		doReturn(user1).when(ur).save(newUser);
		doReturn(false).when(ur).existsById(newUser.getUid());

		User testUser = us.createUser(newUser);

		assertEquals(testUser, user1);
	}

	@Test
	public void createUserAlreadyExists() {
		User newUser = new User(1, "example@revature.com", "password", "Test fname", "Test fname", roles, location,
				true);

		doReturn(true).when(ur).existsById(newUser.getUid());

		User testUser = us.createUser(newUser);

		assertNull(testUser);
	}

	@Test
	public void updateUserNotExists() {
		User updateUser = new User("example@revature.com", "password", "Test fname", "Test fname", roles, location,
				true);

		doReturn(false).when(ur).existsById(updateUser.getUid());

		User testUser = us.updateUser(updateUser);

		assertNull(testUser);
	}

	@Test
	public void updateUserValid() {
		doReturn(user1).when(ur).save(user1);
		doReturn(true).when(ur).existsById(user1.getUid());

		User testUser = us.updateUser(user1);

		assertEquals(testUser, user1);
	}

	@Test
	public void deleteUserTrue() {
		doNothing().when(ur).delete(user1);

		assertTrue(us.deleteUser(user1));
	}

	@Test
	public void deleteUserFalse() {
		doThrow(new EmptyResultDataAccessException(user1.getUid())).when(ur).delete(user1);

		assertFalse(us.deleteUser(user1));
	}

}
