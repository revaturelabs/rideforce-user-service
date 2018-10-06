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
import com.revature.rideforce.user.beans.Office;
import com.revature.rideforce.user.repository.OfficeRepository;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
@Transactional
public class OfficeRepositoryTest {

	@Autowired
	private OfficeRepository repository;
	
	@Before
	public void validate() {
		Assertions.assertThat(repository).isNotNull();
	}
	
	@Test
	public void officeRepositoryCanFindById() {
		Office office = new Office();
		office.setId(100);
		office.setName("Bobby");
		office.setAddress("5125 Venom Lang Rd");
		Office returnedOffice = repository.save(office);
		repository.findById(returnedOffice.getId());
		Assertions.assertThat(repository.findById(returnedOffice.getId())).isNotNull();
	}
	
	@Test
	public void nonExistantRepositoryWillReturnNull() {
		Office office = repository.findById(99990);
		Assertions.assertThat(office).isNull();
	}
}
