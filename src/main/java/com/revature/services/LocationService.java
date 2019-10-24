package com.revature.services;

import java.util.List;

import com.revature.models.Location;

public interface LocationService {

	/**
	 * Create a {@link Location} and save to database
	 * @param l - {@link Location} to create
	 * @return {@link Location} - newly created {@link Location}
	 */
	public Location createLocation(Location l);

	/**
	 * Get all {@link Location}s
	 * @return List<{@link Location}> - List of all {@link Location}s
	 */
	public List<Location> getLocations();

	/**
	 * Get a {@link Location} by lid
	 * @param lid - lid of {@link Location}
	 * @return {@link Location} - {@link Location} with specified lid
	 */
	public Location getLocation(int lid);

	/**
	 * Update a {@link Location}
	 * @param l - {@link Location} with updated fields
	 * @return {@link Location} - updated {@link Location}
	 */
	public Location updateLocation(Location l);

	/**
	 * Delete a {@link Location} by lid
	 * @param lid - lid of {@link Location}
	 * @return boolean - whether the deletion was successful
	 */
	public boolean deleteLocation(int lid);

}
