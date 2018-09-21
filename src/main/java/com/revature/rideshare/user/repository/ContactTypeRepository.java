package com.revature.rideshare.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.rideshare.user.beans.ContactType;

@Repository
public interface ContactTypeRepository extends JpaRepository<ContactType, Integer> {
	ContactType findByTypeIgnoreCase(String type);
}
