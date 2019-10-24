package com.revature.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.revature.models.Location;

@Repository
public interface LocationRepository extends CrudRepository<Location, Integer>{
  
  public Location findByAddress(String address);
  
}
