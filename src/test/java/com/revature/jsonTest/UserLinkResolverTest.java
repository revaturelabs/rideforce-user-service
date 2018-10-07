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
import com.revature.rideforce.user.beans.Office;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.json.UserLinkResolver;
import com.revature.rideforce.user.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
public class UserLinkResolverTest {

	@Autowired
	private UserLinkResolver userLinkResolver;

	@MockBean
	UserRepository userRepository;

	@Before
	public void validate() {
		Assertions.assertThat(userLinkResolver).isNotNull();

		User user = new User();
		user.setId(878);
		user.setEmail("admin@revature.com");
		user.setFirstName("Ricky");
		user.setLastName("Bobby");

		when(userRepository.findById(878)).thenReturn(user);
	}

	@Test(expected = IllegalArgumentException.class)
	public void nullStringWillThrowIllegalArgumentException() {
		userLinkResolver.resolve(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidStringWillThrowIllegalArgumentException() {
		String url = "/users/nine";
		userLinkResolver.resolve(url);
	}

	@Test
	public void validStringWillReturnUserLink() {
		String url = "/users/878";
		User user = userLinkResolver.resolve(url);
		Assertions.assertThat(user).isNotNull();
		Assertions.assertThat(user.getId()).isEqualTo(878);
		Assertions.assertThat(user.getFirstName()).isEqualToIgnoringCase("Ricky");
		Assertions.assertThat(user.getLastName()).isEqualToIgnoringCase("Bobby");
		Assertions.assertThat(user.getEmail()).isEqualTo("admin@revature.com");
	}
}
