package com.revature.repositoryTests;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.revature.rideforce.user.UserApplication;
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
}
