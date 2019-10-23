package com.revature.jsonTest;

import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.rideforce.user.UserApplication;
import com.revature.rideforce.user.beans.ContactType;
import com.revature.rideforce.user.json.ContactTypeResolver;
import com.revature.rideforce.user.services.ContactTypeService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class ContactTypeResolverTest {

	@Autowired
	ContactTypeResolver contactTypeResolver;
	
	@MockBean
	ContactTypeService contactTypeService;
	
	@Before
	public void validate() {
		Assertions.assertThat(contactTypeResolver).isNotNull();
		Assertions.assertThat(contactTypeService).isNotNull();
		
		ContactType type = new ContactType();
		type.setId(878);
		type.setType("ADMIN");
		
		when(contactTypeService.findByType("admin")).thenReturn(type);
		when(contactTypeService.findByType("test_type")).thenReturn(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void nullInputWillThrowIllegalArgumentException() {
		contactTypeResolver.resolve(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void inputWithNoTypeWillThrowIllegalArgumentException() {
		contactTypeResolver.resolve("test_type");
	}
	
	@Test
	public void validTypeWillResolve() {
		ContactType type = contactTypeResolver.resolve("admin");
		Assertions.assertThat(type).isNotNull();
		Assertions.assertThat(type.getId()).isEqualTo(878);
		Assertions.assertThat(type.getType()).isEqualToIgnoringCase("admin");
	}
}
