package com.revature.rideshare.user.services;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.rideshare.user.beans.ContactType;
import com.revature.rideshare.user.repository.ContactTypeRepository;

@Service
public class ContactTypeService {
	@Autowired
	private ContactTypeRepository contactTypeRepository;
	
	public ContactType findByType(String type) {
		return contactTypeRepository.findByTypeIgnoreCase(type);
	}

	public ContactType save(@Valid ContactType type) {
		return contactTypeRepository.save(type);
	}
}
