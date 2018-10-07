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
import com.revature.rideforce.user.beans.ContactType;
import com.revature.rideforce.user.repository.ContactTypeRepository;
import com.revature.rideforce.user.services.ContactTypeService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
public class ContactTypeServiceTest {
	
	@TestConfiguration
	static class ContactTypeServiceContextConfig {
		private ContactTypeRepository contactTypeRepository;
		
		@Bean
		public ContactTypeService contactTypeService(ContactTypeRepository contactTypeRepository) {
			this.contactTypeRepository = contactTypeRepository;
			return new ContactTypeService(contactTypeRepository);
		}
	}

	@Autowired
	private ContactTypeService contactTypeService;
	
	@MockBean
	private ContactTypeRepository contactTypeRepository;
	
	@Before
	public void validate() {
		Assertions.assertThat(contactTypeService).isNotNull();
		Assertions.assertThat(contactTypeRepository).isNotNull();
		ContactType contactType = new ContactType();
		contactType.setId(988);
		contactType.setType("test_type");
		Mockito.when(contactTypeRepository.findByTypeIgnoreCase("test_type"))
			.thenReturn(contactType);
	}
	
	@Test
	public void nullContactTypeWillReturnNull() {
		ContactType returnedType = contactTypeService.findByType(null);
		Assertions.assertThat(returnedType).isNull();
	}
	
	@Test
	public void canFindByType() {
		ContactType returnedType = contactTypeService.findByType("test_type");
		Assertions.assertThat(returnedType).isNotNull();
		Assertions.assertThat(returnedType.getType()).isEqualToIgnoringCase("test_type");
		Assertions.assertThat(returnedType.getId()).isEqualTo(988);
	}
	
	// other methods are protected
	
	
}
