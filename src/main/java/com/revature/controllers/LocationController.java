package com.revature.controllers;

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.Location;

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
	 * Return a location by lid
	 * 
	 * @param lid - lid of Location
	 * @return null
	 */
	@GetMapping(value = "{lid}")
	public Location getLocationById(@PathVariable("lid") int lid) {
		return null;
	}

	/**
	 * Returns all the Locations
	 * 
	 * @return null
	 */
	@GetMapping()
	public List<Location> getLocation() {
		return null;
	}

	/**
	 * Creates a new location
	 * 
	 * @param location - Location to be created
	 * @return null
	 */
	@PostMapping(consumes = "application/json")
	public Location createLocation(@RequestBody Location location) {
		return null;
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
		return null;
	}

	/**
	 * Delete a location by lid
	 * 
	 * @param lid - lid of Location
	 */
	@DeleteMapping(value = "{lid}")
	public void deleteLocation(@PathVariable("lid") int lid) {

	}
}
