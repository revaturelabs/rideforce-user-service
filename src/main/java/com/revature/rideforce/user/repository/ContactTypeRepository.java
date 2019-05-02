package com.revature.rideforce.user.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.revature.rideforce.user.beans.ContactType;

@Repository
@Transactional
public interface ContactTypeRepository extends JpaRepository<ContactType, Integer> {
	ContactType findByTypeIgnoreCase(String type);
	ContactType findById(int id);
}
