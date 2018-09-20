package com.revature.rideshare.user.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.rideshare.user.beans.ContactInfo;
import com.revature.rideshare.user.beans.User;
import com.revature.rideshare.user.repository.ContactInfoRepository;

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
