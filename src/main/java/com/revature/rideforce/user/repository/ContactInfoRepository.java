package com.revature.rideforce.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.revature.rideforce.user.beans.ContactInfo;

@Repository
@Transactional
public interface ContactInfoRepository extends JpaRepository<ContactInfo, Integer> {
	public ContactInfo findById(int id);

	public List<ContactInfo> findByUserId(int id);
}
