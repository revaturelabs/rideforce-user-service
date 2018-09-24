package com.revature.rideshare.user.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.rideshare.user.beans.ContactInfo;
import com.revature.rideshare.user.beans.User;
import com.revature.rideshare.user.repository.ContactInfoRepository;

@Service
public class ContactInfoService implements CrudService<ContactInfo> {
	@Autowired
	ContactInfoRepository contactInfoRepository;

	@Override
	public List<ContactInfo> findAll() {
		return contactInfoRepository.findAll();
	}
	
	@Override
	public ContactInfo findById(int id) {
		return contactInfoRepository.findById(id);
	}
	
	public List<ContactInfo> findByUser(User user) {
		return contactInfoRepository.findByUserId(user.getId());
	}
	
	@Override
	public ContactInfo add(ContactInfo contactInfo) {
		// Ensure that a new entity is created.
		contactInfo.setId(0);
		return contactInfoRepository.save(contactInfo);
	}
	
	@Override
	public ContactInfo save(ContactInfo contactInfo) {
		return contactInfoRepository.save(contactInfo);
	}
}
