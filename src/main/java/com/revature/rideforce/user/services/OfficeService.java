package com.revature.rideforce.user.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.rideforce.user.beans.Office;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.repository.OfficeRepository;

@Service
public class OfficeService extends CrudService<Office> {
	@Autowired
	public OfficeService(OfficeRepository officeRepository) {
		super(officeRepository);
	}
	
	@Override
	protected boolean canFindAll(User user) {
		return true;
	}
	
  /**
    checks if User has permission to find the {@code Office}
    @param User
    @param Office
    */
	@Override
	protected boolean canFindOne(User user, Office obj) {
		return true;
	}
}
