package com.revature.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
		location1 = new Location(1, "1600 Amphitheatre Parkway", "Mountain View", "CA", "94043", 37.42157560,
				-122.08378730);
		location2 = new Location(2, "1 Apple Park Way", "Cupertino", "CA", "95014", 37.3346326, -122.0126824);

	}

	@Before
	public void setUp() {
		ls.setGeoApiContext(new GeoApiContext.Builder().apiKey(apiKey).build());
	}

	@Test
	public void contexLoads() throws Exception {
		assertThat(ls).isNotNull();
	}

	@Test
	public void createLocation() throws ApiException, InterruptedException, IOException {
		Location withoutId = new Location("1600 Amphitheatre Parkway", "Mountain View", "CA", "94043", 37.42157560,
				-122.08378730);

		Location newLocation = new Location("1600 Amphitheatre Parkway", "Mountain View", "CA", "94043");

		doReturn(location1).when(lr).save(withoutId);

		Location testLocation = ls.createLocation(newLocation);

		assertEquals(testLocation, location1);

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
	public void updateLocation() throws ApiException, InterruptedException, IOException {
		doReturn(location1).when(lr).save(location1);

		Location testLocation = ls.updateLocation(location1);

		assertEquals(testLocation, location1);
	}

	@Test
	public void deleteLocation() {
		doNothing().when(lr).deleteById(1);

		assertEquals(ls.deleteLocation(1), true);

		doThrow(new IllegalArgumentException()).when(lr).deleteById(1);

		assertEquals(ls.deleteLocation(1), false);
	}

	@Test
	public void getLatLng() {
		LatLng actualLatLng = new LatLng(37.42157560, -122.08378730);

		LatLng testLatLng = ls.getLatLng("1600 Amphitheatre Parkway Mountain View CA 94043");

		assertEquals(actualLatLng, testLatLng);
	}

}
