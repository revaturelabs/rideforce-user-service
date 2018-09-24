package com.revature.rideshare.user.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.rideshare.user.beans.ContactType;
import com.revature.rideshare.user.exceptions.DuplicateContactTypeException;
import com.revature.rideshare.user.exceptions.EntityConflictException;
import com.revature.rideshare.user.repository.ContactTypeRepository;

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
}
