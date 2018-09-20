package com.revature.rideshare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.rideshare.user.beans.Office;

public interface OfficeRepository extends JpaRepository<Office, Integer>{

}
