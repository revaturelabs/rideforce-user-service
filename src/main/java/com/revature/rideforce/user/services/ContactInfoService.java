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
	
  /**
    Checks if a user can add ContactInfo
    @param User
    @param ContactInfo
    @return true if user is admin or owner of ContactInfo
    */
	@Override
	protected boolean canAdd(User user, ContactInfo obj) {
		// Users can only add their own contact info, except for admins who can
		// do so for any user.
		return user != null && (user.isAdmin() || user.getId() == obj.getUser().getId());
	}
	
  /**
    Checks if a user can save ContactInfo
    @param User
    @param ContactInfo
    @return true if user is admin or owner of ContactInfo
    */
	@Override
	protected boolean canSave(User user, ContactInfo obj) {
		// Users can only save their own contact info, except for admins who can
		// do so for any user.
		return user != null && (user.isAdmin() || user.getId() == obj.getUser().getId());
	}
}
