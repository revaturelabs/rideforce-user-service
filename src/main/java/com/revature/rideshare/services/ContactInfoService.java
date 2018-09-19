package com.revature.rideshare.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.revature.rideshare.repository.ContactInfoRepository;
import com.revature.rideshare.user.beans.ContactInfo;
import com.revature.rideshare.user.beans.User;

public class ContactInfoService {
	@Autowired
	ContactInfoRepository contactInfoRepository;
	
	public List<ContactInfo> getOne(User user) {
		return contactInfoRepository.findByUser(user.getId());
		
	}
}
