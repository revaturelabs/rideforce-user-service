package com.revature.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

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

import com.revature.models.Role;
import com.revature.repos.RoleRepo;

@RunWith(MockitoJUnitRunner.class)
public class RoleServiceTest {

	@Mock
	private RoleRepo rr;

	@InjectMocks
	private RoleServiceImpl rs;
	
	private static Role role1;
	private static Role role2;
	
	@BeforeClass
	public static void setUp() {
		role1 = new Role(1, "Test Role");
		role2 = new Role(2, "Test Role 2");
	}
	
	@Test
	public void contexLoads() throws Exception {
		assertThat(rs).isNotNull();
	}

	@Test
	public void createRole() {
		Role newRole = new Role("Test Role");

		doReturn(role1).when(rr).save(newRole);

		Role testRole = rs.createRole(newRole);

		assertEquals(testRole, role1);
	}

	@Test
	public void getRoleById() {
		doReturn(Optional.of(role1)).when(rr).findById(1);

		Role testRole = rs.getRoleById(1);

		assertEquals(testRole, role1);
	}

	@Test
	public void getByRname() {
		doReturn(role1).when(rr).findByRname("Test Role");

		Role testRole = rs.getByRname("Test Role");

		assertEquals(testRole, role1);
	}

	@Test
	public void getAllRoles() {
		Iterable<Role> actualRoles = new ArrayList<Role>(Arrays.asList(role1, role2));

		doReturn(actualRoles).when(rr).findAll();

		List<Role> testRoles = rs.getAllRoles();

		assertEquals(testRoles, actualRoles);
	}

}
