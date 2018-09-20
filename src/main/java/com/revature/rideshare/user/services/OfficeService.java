package com.revature.rideshare.user.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.rideshare.user.beans.Office;
import com.revature.rideshare.user.repository.OfficeRepository;

@Service
public class OfficeService {
	@Autowired
	OfficeRepository officeRepository;
	
	public List<Office> findAll() {
		return officeRepository.findAll();
	}
	
	public Office findById(int id) {
		return officeRepository.findById(id);
	}
	
	public Office save(Office office) {
		return officeRepository.save(office);
	}
}
