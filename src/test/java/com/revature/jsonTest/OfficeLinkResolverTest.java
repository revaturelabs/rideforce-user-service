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
import com.revature.rideforce.user.json.OfficeLinkResolver;
import com.revature.rideforce.user.repository.OfficeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
public class OfficeLinkResolverTest {

	@Autowired
	private OfficeLinkResolver officeLinkResolver;
	
	@MockBean
	private OfficeRepository officeRepository;
	
	@Before
	public void validate() {
		Assertions.assertThat(officeLinkResolver).isNotNull();
		
		Office office = new Office();
		office.setId(878);
		office.setAddress("5125 Everlast Ln.");
		office.setName("Kentucky");
		when(officeRepository.findById(878)).thenReturn(office);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void nullStringWillThrowIllegalArgumentException() {
		officeLinkResolver.resolve(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidStringWillThrowIllegalArgumentException() {
		String url = "/offices/nine";
		officeLinkResolver.resolve(url);
	}
	
	@Test
	public void validStringWillReturnOffice() {
		String url = "/offices/878";
		Office office = officeLinkResolver.resolve(url);
		Assertions.assertThat(office).isNotNull();
		Assertions.assertThat(office.getId()).isEqualTo(878);
		Assertions.assertThat(office.getAddress().contains("Everlast")).isTrue();
		Assertions.assertThat(office.getName()).isEqualTo("Kentucky");
	}
}
