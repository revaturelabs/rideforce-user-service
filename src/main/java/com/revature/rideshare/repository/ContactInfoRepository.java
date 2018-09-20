package com.revature.rideshare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.rideshare.user.beans.ContactInfo;
import com.revature.rideshare.user.beans.User;

public interface ContactInfoRepository extends JpaRepository<ContactInfo, Integer> {
	public List<ContactInfo> findByUserId(int id);
}
