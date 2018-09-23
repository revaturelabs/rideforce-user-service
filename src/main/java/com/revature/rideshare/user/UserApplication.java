package com.revature.rideshare.user;

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
import com.revature.rideshare.user.json.EnumLikeDeserializerModifier;
import com.revature.rideshare.user.json.EnumLikeSerializerModifier;
import com.revature.rideshare.user.json.LinkDeserializerModifier;
import com.revature.rideshare.user.json.LinkSerializerModifier;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaRepositories
public class UserApplication {
	@Autowired
	private ApplicationContext context;

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
}
