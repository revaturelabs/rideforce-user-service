package com.revature.rideshare.user.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.rideshare.user.beans.ContactType;
import com.revature.rideshare.user.exceptions.DuplicateContactTypeException;
import com.revature.rideshare.user.repository.ContactTypeRepository;

@Service
public class ContactTypeService implements CrudService<ContactType> {
	@Autowired
	private ContactTypeRepository contactTypeRepository;
	
	@Override
	public List<ContactType> findAll() {
		return contactTypeRepository.findAll();
	}
	
	@Override
	public ContactType findById(int id) {
		return contactTypeRepository.findById(id);
	}
	
	public ContactType findByType(String type) {
		return contactTypeRepository.findByTypeIgnoreCase(type);
	}
	
	@Override
	public ContactType add(ContactType type) throws DuplicateContactTypeException {
		// Ensure that a new type is created.
		type.setId(0);
		if (findByType(type.getType()) != null) {
			throw new DuplicateContactTypeException(type.getType());
		}
		return contactTypeRepository.save(type);
	}

	@Override
	public ContactType save(ContactType type) throws DuplicateContactTypeException {
		ContactType existing = findByType(type.getType());
		if (existing != null && existing.getId() != type.getId()) {
			throw new DuplicateContactTypeException(type.getType());
		}
		return contactTypeRepository.save(type);
	}
}
