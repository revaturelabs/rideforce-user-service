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
import com.revature.rideforce.user.beans.ContactType;
import com.revature.rideforce.user.repository.ContactTypeRepository;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@Transactional
public class ContactTypeRepositoryTest {

	@Autowired
	private ContactTypeRepository repository;
	
	@Before
	public void validate() {
		Assertions.assertThat(repository).isNotNull();
	}
	
	@Test
	@Ignore
	public void canFindByTypeIgnoreCase() {
		ContactType ct = new ContactType();
		ct.setId(60);
		ct.setType("test_type");
		repository.save(ct);
		ContactType returnedType = repository.findByTypeIgnoreCase("test_type");
		Assertions.assertThat(returnedType).isNotNull();
	}
	
	@Test
	public void canFindById() {
		ContactType ct = new ContactType();
		ct.setId(60);
		ct.setType("test_type");
		ContactType savedType = repository.save(ct);
		ContactType returnedType = repository.findById(savedType.getId());
		Assertions.assertThat(returnedType).isNotNull();
	}
	
	@Test
	public void returnsNullWhenNoType() {
		ContactType returnedType = repository.findByTypeIgnoreCase("ricky_bobby");
		Assertions.assertThat(returnedType).isNull();
	}
	
	@Test
	public void returnsNullWhenInvalidTypeId() {
		Assertions.assertThat(repository.findById(9993)).isNull();
	}
}
