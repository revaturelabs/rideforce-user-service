package com.revature.rideforce.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.rideforce.user.beans.Office;

@Repository
public interface OfficeRepository extends JpaRepository<Office, Integer>{
	public Office findById(int id);
}
