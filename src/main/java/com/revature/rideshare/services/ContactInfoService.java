package com.revature.rideshare.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.rideshare.repository.ContactInfoRepository;
import com.revature.rideshare.user.beans.ContactInfo;
import com.revature.rideshare.user.beans.User;

@Service
public class ContactInfoService {
	@Autowired
	ContactInfoRepository contactInfoRepository;
	
	public ContactInfo findById(int id) {
		return contactInfoRepository.findById(id);
	}
	
	public List<ContactInfo> findByUser(User user) {
		return contactInfoRepository.findByUserId(user.getId());
	}
	
	public ContactInfo save(ContactInfo contactInfo) {
		return contactInfoRepository.save(contactInfo);
	}
}
