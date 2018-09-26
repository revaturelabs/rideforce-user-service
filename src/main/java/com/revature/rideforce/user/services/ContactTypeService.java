package com.revature.rideforce.user.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.rideforce.user.beans.ContactType;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.exceptions.DuplicateContactTypeException;
import com.revature.rideforce.user.exceptions.EntityConflictException;
import com.revature.rideforce.user.repository.ContactTypeRepository;

@Service
public class ContactTypeService extends CrudService<ContactType> {
	private ContactTypeRepository contactTypeRepository;

	@Autowired
	public ContactTypeService(ContactTypeRepository contactTypeRepository) {
		super(contactTypeRepository);
		this.contactTypeRepository = contactTypeRepository;
	}
	
	public ContactType findByType(String type) {
		return contactTypeRepository.findByTypeIgnoreCase(type);
	}
	
	@Override
	protected void throwOnConflict(ContactType obj) throws EntityConflictException {
		ContactType existing = findByType(obj.getType());
		if (existing != null && existing.getId() != obj.getId()) {
			throw new DuplicateContactTypeException(obj.getType());
		}
	}
	
	@Override
	protected boolean canFindAll(User user) {
		return true;
	}
	
	@Override
	protected boolean canFindOne(User user, ContactType obj) {
		return true;
	}
}
