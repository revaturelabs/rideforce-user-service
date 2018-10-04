package com.revature.rideforce.user.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.rideforce.user.beans.Office;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.repository.OfficeRepository;

/**
 * Service layer for {@linkplain Office}. Its main use is to verify if the current logged in user can retrieve all
 * Office objects or retrieve a specific Office.
 * <p><strong>Member Variables</strong><br>
 * {@linkplain OfficeRepository} officeRepository
 * @author clpeng
 * @since Iteration1 10/01/2018
 */
@Service
public class OfficeService extends CrudService<Office> {
	@Autowired
	public OfficeService(OfficeRepository officeRepository) {
		super(officeRepository);
	}
	
	/* (non-Javadoc)
	 * @see com.revature.rideforce.user.services.CrudService#canFindAll(com.revature.rideforce.user.beans.User)
	 */
	@Override
	protected boolean canFindAll(User user) {
		return true;
	}
	

  /**
    checks if User has permission to find the {@code Office}
    @param User
    @param Office
    */

	/* (non-Javadoc)
	 * @see com.revature.rideforce.user.services.CrudService#canFindOne(com.revature.rideforce.user.beans.User, com.revature.rideforce.user.beans.Identifiable)
	 */

	@Override
	protected boolean canFindOne(User user, Office obj) {
		return true;
	}
}
