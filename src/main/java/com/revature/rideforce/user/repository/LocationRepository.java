package com.revature.rideforce.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.rideforce.user.beans.CachedLocation;

@Repository
public interface LocationRepository extends JpaRepository<CachedLocation, Integer>{
	
}
