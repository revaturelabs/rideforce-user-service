package com.revature.rideshare.user.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.rideshare.user.beans.Office;
import com.revature.rideshare.user.repository.OfficeRepository;

@Service
public class OfficeService implements CrudService<Office> {
	@Autowired
	OfficeRepository officeRepository;
	
	@Override
	public List<Office> findAll() {
		return officeRepository.findAll();
	}
	
	@Override
	public Office findById(int id) {
		return officeRepository.findById(id);
	}
	
	@Override
	public Office add(Office office) {
		// Ensure that a new office is created.
		office.setId(0);
		return officeRepository.save(office);
	}
	
	@Override
	public Office save(Office office) {
		return officeRepository.save(office);
	}
}
