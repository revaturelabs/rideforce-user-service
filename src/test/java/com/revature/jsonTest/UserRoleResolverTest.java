package com.revature.jsonTest;

import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.rideforce.user.UserApplication;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.beans.UserRole;
import com.revature.rideforce.user.json.UserRoleResolver;
import com.revature.rideforce.user.repository.UserRoleRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
public class UserRoleResolverTest {

	@Autowired
	private UserRoleResolver userRoleResolver;
	
	@MockBean
	private UserRoleRepository userRoleRepository;
	
	@Before
	public void validate() {
		Assertions.assertThat(userRoleResolver).isNotNull();
		
		UserRole role = new UserRole();
		
		role.setId(878);
		role.setType("ADMIN");
		
		when(userRoleRepository.findByTypeIgnoreCase("ADMIN")).thenReturn(role);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void nullStringWillThrowIllegalArgumentException() {
		userRoleResolver.resolve(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidStringWillThrowIllegalArgumentException() {
		String url = "/users/nine";
		userRoleResolver.resolve(url);
	}

	@Test
	public void validStringWillReturnUserLink() {
		String value = "ADMIN";
		UserRole role = userRoleResolver.resolve(value);
		Assertions.assertThat(role).isNotNull();
		Assertions.assertThat(role.getId()).isEqualTo(878);
		Assertions.assertThat(role.getType()).isEqualTo("ADMIN");
	}
}
