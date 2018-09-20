package com.revature.rideshare.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.revature.rideshare.repository.OfficeRepository;
import com.revature.rideshare.user.beans.Office;

public class OfficeService {
	
	@Autowired
	OfficeRepository officeRepository;
	
	public List<Office> getAll() {
		return officeRepository.findAll();
		
	}
}
