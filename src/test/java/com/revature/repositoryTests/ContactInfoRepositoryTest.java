package com.revature.repositoryTests;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.revature.rideforce.user.UserApplication;
import com.revature.rideforce.user.beans.ContactInfo;
import com.revature.rideforce.user.beans.ContactType;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.repository.ContactInfoRepository;
import com.revature.rideforce.user.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@Transactional
public class ContactInfoRepositoryTest {
	
	@Autowired
	private ContactInfoRepository repository;
	
	@Autowired
	private UserRepository userRepo;
	
	@Before
	public void validate() {
		Assertions.assertThat(repository).isNotNull();
		Assertions.assertThat(userRepo).isNotNull();
	}
	
	@Test
	public void contactInfoRepositoryCanFindById() {
		User user = userRepo.findById(1);
		ContactInfo contactInfo = new ContactInfo();
		contactInfo.setId(60);
		contactInfo.setInfo("2705371242");
		contactInfo.setUser(user);
		contactInfo.setType(new ContactType(100, "phone"));
		ContactInfo returnInfo = repository.save(contactInfo);
		Assertions.assertThat(returnInfo).isNotNull();
		Assertions.assertThat(repository.findById(returnInfo.getId())).isNotNull();
		Assertions.assertThat(repository.findById(returnInfo.getId())).isEqualTo(returnInfo);
	}
	
	@Test
	@Ignore
	public void contactInfoRepositoryCanFindByUserId() {
		User user = userRepo.findById(1);
		ContactInfo contactInfo = new ContactInfo();
		contactInfo.setId(60);
		contactInfo.setInfo("270537676");
		contactInfo.setUser(user);
		contactInfo.setType(new ContactType(100, "phone"));
		ContactInfo returnInfo = repository.save(contactInfo);
		Assertions.assertThat(returnInfo).isNotNull();
		Assertions.assertThat(repository.findByUserId(returnInfo.getUser().getId())).isNotNull();
	}
	
	@Test
	public void nonExistantFindByIdReturnsNull() {
		User user = userRepo.findById(1);
		ContactInfo contactInfo = new ContactInfo();
		contactInfo.setId(60);
		contactInfo.setInfo("2705376767");
		contactInfo.setUser(user);
		contactInfo.setType(new ContactType(100, "phone"));
		ContactInfo returnInfo = repository.save(contactInfo);
		Assertions.assertThat(returnInfo).isNotNull();
		Assertions.assertThat(repository.findById(99999)).isNull();
	}
	
	@Test
	public void nonExistantUserReturnsNullWhenSearchingByUserId() {
		Assertions.assertThat(repository.findByUserId(99999)).isEmpty();
	}
}
