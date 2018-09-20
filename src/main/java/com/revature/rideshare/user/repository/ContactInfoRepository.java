package com.revature.rideshare.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.rideshare.user.beans.ContactInfo;

public interface ContactInfoRepository extends JpaRepository<ContactInfo, Integer> {
	public ContactInfo findById(int id);

	public List<ContactInfo> findByUserId(int id);
}
