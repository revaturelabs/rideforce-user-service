package com.revature.rideforce.user.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.rideforce.user.beans.ContactInfo;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.repository.ContactInfoRepository;

@Service
public class ContactInfoService extends CrudService<ContactInfo> {
	ContactInfoRepository contactInfoRepository;

	@Autowired
	public ContactInfoService(ContactInfoRepository contactInfoRepository) {
		super(contactInfoRepository);
		this.contactInfoRepository = contactInfoRepository;
	}
	
	public List<ContactInfo> findByUser(User user) {
		return contactInfoRepository.findByUserId(user.getId());
	}
}
