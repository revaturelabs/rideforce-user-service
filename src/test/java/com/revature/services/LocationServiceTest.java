package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.EmptyResultDataAccessException;

import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.LatLng;
import com.revature.models.Location;
import com.revature.repos.LocationRepository;

@RunWith(MockitoJUnitRunner.class)
public class LocationServiceTest {

	@Mock
	private LocationRepository lr;

	private String apiKey = System.getenv("MAPS_API_KEY");
	@InjectMocks
	private LocationServiceImpl ls;

	private static Location location1;
	private static Location location2;

	@BeforeClass
	public static void setUpAll() {
		location1 = new Location(1, "1600 Amphitheatre Parkway", "Mountain View", "CA", "94043", 37.4216124,
				-122.083792);
		location2 = new Location(2, "1 Apple Park Way", "Cupertino", "CA", "95014", 37.3346326, -122.0126824);

	}

	@Before
	public void setUp() {
		ls.setGeoApiContext(new GeoApiContext.Builder().apiKey(apiKey).build());
	}

	@Test
	public void contexLoads() throws Exception {
		assertNotNull(ls);
	}

	@Test
	public void createLocationValid() throws ApiException, InterruptedException, IOException {
		Location withoutId = new Location(0, "1600 Amphitheatre Parkway", "Mountain View", "CA", "94043", 37.4216124,
				-122.083792);

		Location newLocation = new Location(0, "1600 Amphitheatre Parkway", "Mountain View", "CA", "94043");

		doReturn(location1).when(lr).save(withoutId);
		doReturn(false).when(lr).existsById(newLocation.getLid());

		Location testLocation = ls.createLocation(newLocation);

		assertEquals(testLocation, location1);
	}

	@Test
	public void createLocationAlreadyExists() {
		Location newLocation = new Location(1, "1600 Amphitheatre Parkway", "Mountain View", "CA", "94043");

		doReturn(true).when(lr).existsById(newLocation.getLid());

		Location testLocation = ls.createLocation(newLocation);

		assertNull(testLocation);
	}

	@Test
	public void createLocationInvalidAddress() {
		Location newLocation = new Location("1600 Amphitheatre Parkway", "Mountain View", "CA", "94043");
		newLocation.setAddress(null);

		Location testLocation = ls.createLocation(newLocation);

		assertNull(testLocation);
	}

	@Test
	public void createLocationInvalidCity() {
		Location newLocation = new Location("1600 Amphitheatre Parkway", "Mountain View", "CA", "94043");
		newLocation.setCity(null);

		Location testLocation = ls.createLocation(newLocation);

		assertNull(testLocation);
	}

	@Test
	public void createLocationInvalidState() {
		Location newLocation = new Location("1600 Amphitheatre Parkway", "Mountain View", "CA", "94043");
		newLocation.setState(null);

		Location testLocation = ls.createLocation(newLocation);

		assertNull(testLocation);
	}

	@Test
	public void createLocationInvalidZip() {
		Location newLocation = new Location("1600 Amphitheatre Parkway", "Mountain View", "CA", "94043");
		newLocation.setZip(null);

		Location testLocation = ls.createLocation(newLocation);

		assertNull(testLocation);
	}

	@Test
	public void getLocations() {
		Iterable<Location> actualLocations = new ArrayList<Location>(Arrays.asList(location1, location2));

		doReturn(actualLocations).when(lr).findAll();

		List<Location> testLocations = ls.getLocations();

		assertEquals(testLocations, actualLocations);
	}

	@Test
	public void getLocation() {
		doReturn(Optional.of(location1)).when(lr).findById(1);

		Location testLocation = ls.getLocation(1);

		assertEquals(testLocation, location1);
	}

	@Test
	public void updateLocationValid() throws ApiException, InterruptedException, IOException {
		doReturn(location1).when(lr).save(location1);
		doReturn(true).when(lr).existsById(location1.getLid());

		Location testLocation = ls.updateLocation(location1);

		assertEquals(testLocation, location1);
	}

	@Test
	public void deleteLocationTrue() {
		doNothing().when(lr).deleteById(location1.getLid());

		assertTrue(ls.deleteLocation(location1.getLid()));
	}

	@Test
	public void deleteLocationFalse() {
		doThrow(new EmptyResultDataAccessException(location1.getLid())).when(lr).deleteById(location1.getLid());

		assertFalse(ls.deleteLocation(location1.getLid()));
	}

	@Test
	public void getLatLng() {
		LatLng actualLatLng = new LatLng(37.4216124, -122.083792);

		LatLng testLatLng = ls.getLatLng("1600 Amphitheatre Parkway Mountain View CA 94043");

		assertEquals(actualLatLng, testLatLng);
	}

}
