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

import com.revature.models.Role;
import com.revature.models.User;
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
		assertNotNull(rs);
	}

	@Test
	public void createRoleValid() {
		Role newRole = new Role(0, "Test Role");

		doReturn(role1).when(rr).save(newRole);
		doReturn(false).when(rr).existsById(newRole.getId());

		Role testRole = rs.createRole(newRole);

		assertEquals(testRole, role1);
	}
	
	@Test 
	public void createRoleAlreadyExists() {
		Role newRole = new Role(1, "Test Role");
		
		doReturn(true).when(rr).existsById(newRole.getId());
		
		Role testRole = rs.createRole(newRole);
		
		assertNull(testRole);
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
	
	@Test
	public void updateRoleValid() {
		doReturn(role1).when(rr).save(role1);
		doReturn(true).when(rr).existsById(role1.getId());

		Role testRole = rs.updateRole(role1);

		assertEquals(testRole, role1);
	}

	@Test
	public void updateRoleNotExists() {
		Role updateRole = new Role(50, "Update Role");

		doReturn(false).when(rr).existsById(updateRole.getId());

		Role testRole = rs.updateRole(updateRole);

		assertNull(testRole);
	}
	
	@Test
	public void deleteRoleTrue() {
		doNothing().when(rr).deleteById(role1.getId());

		assertTrue(rs.deleteRole(role1.getId()));
	}

	@Test
	public void deleteRoleFalse() {
		doThrow(new EmptyResultDataAccessException(role1.getId())).when(rr).deleteById(role1.getId());

		assertFalse(rs.deleteRole(role1.getId()));
	}

}
