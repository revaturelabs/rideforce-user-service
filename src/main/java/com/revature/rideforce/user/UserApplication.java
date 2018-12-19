package com.revature.rideforce.user;

import java.util.HashSet;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.revature.rideforce.user.beans.Office;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.beans.UserRole;
import com.revature.rideforce.user.json.EnumLikeDeserializerModifier;
import com.revature.rideforce.user.json.EnumLikeSerializerModifier;
import com.revature.rideforce.user.json.LinkDeserializerModifier;
import com.revature.rideforce.user.json.LinkSerializerModifier;
import com.revature.rideforce.user.repository.OfficeRepository;
import com.revature.rideforce.user.repository.UserRepository;
import com.revature.rideforce.user.repository.UserRoleRepository;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaRepositories
public class UserApplication implements InitializingBean {
	
	private static final String ADMIN = "ADMIN";
	@Autowired
	private ApplicationContext context;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OfficeRepository officeRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	/**
	 * Starts the Spring Application
	 * @param 	args 		String[]
	 * @throws 	Exception 	throws any exception
	 */
	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer objectMapperCustomizer() {
		Module jsonLinks = new SimpleModule("json-links").setSerializerModifier(new LinkSerializerModifier())
				.setDeserializerModifier(new LinkDeserializerModifier(context));
		Module jsonEnumLike = new SimpleModule("json-enum-like").setSerializerModifier(new EnumLikeSerializerModifier())
				.setDeserializerModifier(new EnumLikeDeserializerModifier(context));

		return builder -> builder.modules(jsonLinks, jsonEnumLike);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// Create a dummy admin if none exist.
		if (userRepository.findAll().isEmpty()) {
			if (officeRepository.findAll().isEmpty()) {
				Office reston = new Office();
				reston.setName("Reston");
				reston.setAddress("11730 Plaza America Dr. Reston, VA");
				officeRepository.save(reston);
			}

			if (userRoleRepository.findByTypeIgnoreCase(ADMIN) == null) {
				UserRole adminRole = new UserRole();
				adminRole.setType(ADMIN);
				userRoleRepository.save(adminRole);
			}

			User admin = new User();
			admin.setFirstName("admin");
			admin.setLastName("admin");
			admin.setEmail("admin@revature.com");
			admin.setAddress("11730 Plaza America Dr. Reston, VA");
			admin.setOffice(officeRepository.findAll().get(0));
			admin.setCars(new HashSet<>());
			admin.setContactInfo(new HashSet<>());
			admin.setActive("ACTIVE");
			admin.setRole(userRoleRepository.findByTypeIgnoreCase(ADMIN));
			admin.setPassword("password"); 							//within setPassword is where we can encode it (more modular)
			admin.setStartTime((float) 9.0);
			userRepository.save(admin);
		}
	}
}
