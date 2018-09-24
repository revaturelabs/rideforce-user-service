package com.revature.rideforce.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.rideforce.user.beans.ContactInfo;

public interface ContactInfoRepository extends JpaRepository<ContactInfo, Integer> {
	public ContactInfo findById(int id);

	public List<ContactInfo> findByUserId(int id);
}
