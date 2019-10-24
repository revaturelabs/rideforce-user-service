package com.revature.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.maps.model.LatLng;
import com.revature.models.Location;
import com.revature.services.LocationService;

/**
 * 
 * Responsible for intercepting the HTTP request methods at the /locations
 * endpoint
 * 
 * @author george
 * 
 */
@RequestMapping("locations")
@RestController
@CrossOrigin
public class LocationController {
	
	/**
	 * Inject the LocationService, to do business logic for controller
	 */
	@Autowired
	LocationService ls;

	/**
	 * Return a location by lid
	 * 
	 * @param lid - lid of Location
	 * @return null
	 */
	@GetMapping(value = "{lid}")
	public Location getLocationById(@PathVariable("lid") int lid) {
		return ls.getLocation(lid);
	}

	/**
	 * Returns all the Locations
	 * 
	 * @return null
	 */
	@GetMapping()
	public List<Location> getLocation() {
		return ls.getLocations();
	}

	/**
	 * Creates a new location
	 * 
	 * @param location - Location to be created
	 * @return null
	 */
	@PostMapping(consumes = "application/json")
	public Location createLocation(@RequestBody Location location) {
		return ls.createLocation(location);
	}

	/**
	 * Updates a location by lid
	 * 
	 * @param lid      - lid of Location
	 * @param location - Location with changed information
	 * @return null
	 */
	@PutMapping(value = "{lid}", consumes = "application/json")
	public Location updateLocation(@PathVariable("lid") int lid, @RequestBody Location location) {
		location.setLid(lid);
		return ls.updateLocation(location);
	}

	/**
	 * Delete a location by lid
	 * 
	 * @param lid - lid of Location
	 */
	@DeleteMapping(value = "{lid}")
	public boolean deleteLocation(@PathVariable("lid") int lid) {
		return ls.deleteLocation(lid);
	}
}
