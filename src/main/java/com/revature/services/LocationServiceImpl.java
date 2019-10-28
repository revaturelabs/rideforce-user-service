package com.revature.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.revature.models.Location;
import com.revature.repos.LocationRepository;

@Service
public class LocationServiceImpl implements LocationService {

	/**
	 * Inject the LocationRepository, the way to get a {@link Location} from
	 * database
	 */
	@Autowired
	LocationRepository lr;

	/**
	 * Injecting the GeoApiContext, the entry point for making requests against the
	 * Google Geo APIs.
	 */
	@Autowired
	GeoApiContext geoApiContext;

	/**
	 * Create a {@link Location} and save to database
	 * 
	 * @param l - {@link Location} to create
	 * @return {@link Location} - newly created {@link Location}
	 */
	public Location createLocation(Location l) {
		if (l == null || (l.getLid() != null && lr.existsById(l.getLid())) || l.getAddress() == null
				|| l.getCity() == null || l.getState() == null || l.getZip() == null) {
			return null;
		}
		LatLng latLng = getLatLng(l.getAddress() + " " + l.getCity() + " " + l.getState() + " " + l.getZip());
		if (latLng == null) {
			return null;
		}
		l.setLatitude(latLng.lat);
		l.setLongitude(latLng.lng);
		System.out.println(l);
		return lr.save(l);
	}

	/**
	 * Get all {@link Location}s
	 * 
	 * @return List<{@link Location}> - List of all {@link Location}s
	 */
	public List<Location> getLocations() {
		return (List<Location>) lr.findAll();
	}

	/**
	 * Get a {@link Location} by lid
	 * 
	 * @param lid - lid of {@link Location}
	 * @return {@link Location} - {@link Location} with specified lid
	 */
	public Location getLocation(int lid) {
		return lr.findById(lid).get();
	}

	/**
	 * Update a {@link Location}
	 * 
	 * @param l - {@link Location} with updated fields
	 * @return {@link Location} - updated {@link Location}
	 */
	public Location updateLocation(Location l) {
		if (l == null || l.getLid() == null || !lr.existsById(l.getLid()) || l.getAddress() == null
				|| l.getCity() == null || l.getState() == null || l.getZip() == null) {
			return null;
		}
		LatLng latLng = getLatLng(l.getAddress() + " " + l.getCity() + " " + l.getState() + " " + l.getZip());
		if (latLng == null) {
			return null;
		}
		l.setLatitude(latLng.lat);
		l.setLongitude(latLng.lng);
		return lr.save(l);
	}

	/**
	 * Delete a {@link Location} by lid
	 * 
	 * @param lid - lid of {@link Location}
	 * @return boolean - whether the deletion was successful
	 */
	public boolean deleteLocation(int lid) {
		try {
			lr.deleteById(lid);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Get latitude and longitude from address
	 * 
	 * @param address
	 * @return LatLng (geographical location represented by latitude/longitude pair)
	 */
	public LatLng getLatLng(String address) {
		try {
			GeocodingResult[] results = GeocodingApi.geocode(geoApiContext, address).await();
			if (results.length == 0) {
				return null;
			}
			else {
				return results[0].geometry.location;
			}
		} catch (ApiException | InterruptedException | IOException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
			return null;
		}
	}

	public void setGeoApiContext(GeoApiContext geoApiContext) {
		this.geoApiContext = geoApiContext;

	}
}
