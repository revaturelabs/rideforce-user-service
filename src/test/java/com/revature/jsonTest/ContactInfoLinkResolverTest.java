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
import com.revature.rideforce.user.beans.ContactInfo;
import com.revature.rideforce.user.beans.ContactType;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.json.ContactInfoLinkResolver;
import com.revature.rideforce.user.repository.ContactInfoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=UserApplication.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class ContactInfoLinkResolverTest {

	
	@Autowired
	private ContactInfoLinkResolver contactInfoLinkResolver;
	
	@MockBean
	ContactInfoRepository contactInfoRepository;
	
	@Before
	public void validate() {
		Assertions.assertThat(contactInfoLinkResolver).isNotNull();
		Assertions.assertThat(contactInfoRepository).isNotNull();
		
		ContactInfo info = new ContactInfo();
		info.setId(878);
		info.setInfo("info");
		ContactType type = new ContactType();
		type.setId(78);
		type.setType("ADMIN");
		info.setType(type);
		info.setUser(new User());
		
		when(contactInfoRepository.findById(878)).thenReturn(info);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidLinkThrowsException() {
		String invalidUrl = "/contact-info/bobby";
		contactInfoLinkResolver.resolve(invalidUrl);
	}
	
	@Test
	public void validLinkReturnsContactInfo() {
		String url = "/contact-info/878";
		ContactInfo returnedInfo = contactInfoLinkResolver.resolve(url);
		Assertions.assertThat(returnedInfo).isNotNull();
		Assertions.assertThat(returnedInfo.getId()).isEqualTo(878);
		Assertions.assertThat(returnedInfo.getInfo()).isEqualTo("info");
		Assertions.assertThat(returnedInfo.getType().getType()).isEqualToIgnoringCase("ADMIN");
		Assertions.assertThat(returnedInfo.getUser()).isNotNull();
	}
}
