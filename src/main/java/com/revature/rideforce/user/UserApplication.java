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
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.revature.rideforce.user.beans.Office;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.beans.UserRole;
import com.revature.rideforce.user.json.EnumLikeDeserializerModifier;
import com.revature.rideforce.user.json.EnumLikeSerializerModifier;
import com.revature.rideforce.user.json.LinkDeserializerModifier;
import com.revature.rideforce.user.json.LinkSerializerModifier;
import com.revature.rideforce.user.services.OfficeService;
import com.revature.rideforce.user.services.UserRoleService;
import com.revature.rideforce.user.services.UserService;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaRepositories
public class UserApplication implements InitializingBean {
	@Autowired
	private ApplicationContext context;

	@Autowired
	private UserService userService;

	@Autowired
	private OfficeService officeService;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(UserApplication.class, args);
	}

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer objectMapperCustomizer() {
		Module jsonLinks = new SimpleModule("json-links").setSerializerModifier(new LinkSerializerModifier())
				.setDeserializerModifier(new LinkDeserializerModifier(context));
		Module jsonEnumLike = new SimpleModule("json-enum-like").setSerializerModifier(new EnumLikeSerializerModifier())
				.setDeserializerModifier(new EnumLikeDeserializerModifier(context));

		return builder -> {
			builder.modules(jsonLinks, jsonEnumLike);
		};
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// Create a dummy admin if none exist.
		if (userService.findAll().isEmpty()) {
			if (officeService.findAll().isEmpty()) {
				Office reston = new Office();
				reston.setName("Reston");
				reston.setAddress("11730 Plaza America Dr. Reston, VA");
				officeService.add(reston);
			}

			if (userRoleService.findByType("ADMIN") == null) {
				UserRole adminRole = new UserRole();
				adminRole.setType("ADMIN");
				adminRole = userRoleService.add(adminRole);
			}

			User admin = new User();
			admin.setFirstName("Admin");
			admin.setLastName("Admin");
			admin.setEmail("admin@revature.com");
			admin.setAddress("11730 Plaza America Dr. Reston, VA");
			admin.setOffice(officeService.findAll().get(0));
			admin.setCars(new HashSet<>());
			admin.setContactInfo(new HashSet<>());
			admin.setActive(true);
			admin.setRole(userRoleService.findByType("ADMIN"));
			admin.setPassword(passwordEncoder.encode("password"));
			userService.add(admin);
		}
	}
}
