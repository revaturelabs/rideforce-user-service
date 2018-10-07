package com.revature.serviceTests;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.rideforce.user.UserApplication;
import com.revature.rideforce.user.beans.UserRole;
import com.revature.rideforce.user.exceptions.PermissionDeniedException;
import com.revature.rideforce.user.repository.UserRoleRepository;
import com.revature.rideforce.user.services.AuthenticationService;
import com.revature.rideforce.user.services.UserRoleService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
public class UserRoleServiceTest {

	@TestConfiguration
	static class UserRoleServiceTestContextConfig {
		
		private UserRoleRepository userRoleRepository;
		
		@Bean
		public UserRoleService userRoleService(UserRoleRepository userRoleRepository) {
			this.userRoleRepository = userRoleRepository;
			return new UserRoleService(userRoleRepository);
		}
	}
	
	@Autowired
	private UserRoleService userRoleService;
	
	@MockBean
	private UserRoleRepository userRoleRepository;
	
	@MockBean
	private AuthenticationService authService;
	
	@Before
	public void validate() {
		Assertions.assertThat(userRoleService).isNotNull();
		Assertions.assertThat(userRoleRepository).isNotNull();
		
		UserRole role = new UserRole();
		role.setId(908);
		role.setType("TEST");
		
		Mockito.when(userRoleRepository.findByTypeIgnoreCase("TEST"))
			.thenReturn(role);
	}
	
	@Test
	public void nullRoleTypeWillReturnNull() throws PermissionDeniedException {
		UserRole returnedRole = userRoleService.findByType(null);
		
		Assertions.assertThat(returnedRole).isNull();
	}
	
	@Test
	public void validRoleTypeWillReturnType() throws PermissionDeniedException {
		UserRole returnedRole = userRoleService.findByType("TEST");
		
		Assertions.assertThat(returnedRole).isNotNull();
		Assertions.assertThat(returnedRole.getId()).isEqualTo(908);
		Assertions.assertThat(returnedRole.getType()).isEqualTo("TEST");
	}
	
	
}
