package com.revature.rideforce.user.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * configures the {@linkplain HttpSecurity} - authorizes requests, adds session management, adds filters
 * 
 * @author clpeng, Mateusz Wiater
 * @since Iteration1 10/01/2018
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private JwtFilter jwtFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Matchers for routes that can be accessed without authentication.
		RequestMatcher[] allowable = { new AntPathRequestMatcher("/login", "POST"),
				new AntPathRequestMatcher("/.well-known/jwks.json", "GET"), new AntPathRequestMatcher("/tokens/**"),
				new AntPathRequestMatcher("/actuator/info","GET"), new AntPathRequestMatcher("/actuator/health","GET"),
				new AntPathRequestMatcher("/users/**", "GET"), new AntPathRequestMatcher("/users", "POST"),
				new AntPathRequestMatcher("/offices", "GET"), new AntPathRequestMatcher("/contact-types", "GET"),
				new AntPathRequestMatcher("/roles", "GET"), new AntPathRequestMatcher("/**", "OPTIONS")};

		http.csrf().disable().cors();
		http.authorizeRequests().requestMatchers(allowable).permitAll().anyRequest().authenticated();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
}
