package com.revature.rideforce;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.google.maps.GeoApiContext;

@SpringBootApplication
@ComponentScan("com.revature")
@EnableJpaRepositories("com.revature.repos")
@EntityScan("com.revature.models")
public class RideforceV2UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RideforceV2UserServiceApplication.class, args);
	}
	
	/**
	 * the api key
	 * @Value
	 */
	@Value("${MAPS_API_KEY}")
	private String apiKey;
	
	/**
	 * Generates the API Key
	 * @Bean
	 */
	@Bean
	public GeoApiContext geoApiContext() {
		return new GeoApiContext.Builder().apiKey(apiKey).build();
	}

}
