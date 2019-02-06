package com.revature.rideforce.user.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.revature.rideforce.user.services.AuthenticationService;

/**
 * JWT filter for getting tokens and setting the SecurityContextHolder, setting a user as logged in
 * 
 * @author clpeng, Mateusz Wiater
 * @since Iteration1 10/01/2018
 *
 */
@Service
public class JwtFilter extends OncePerRequestFilter {
	@Autowired
	private AuthenticationService as;
	
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Get the authorization header
        Optional.ofNullable(request.getHeader("Authorization"))
        // Make sure it starts with the 'Bearer' string
        .filter(a -> a.startsWith("Bearer "))
        // Remove the 'Bearer' portion
        .map(a -> a.substring(7))
        // Attempt to authenticate the token
        .map(as::authenticate)
        // If authenticated set the security context
        .ifPresent(SecurityContextHolder.getContext()::setAuthentication);
        
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT,OPTIONS");
//        response.setHeader("Access-Control-Allow-Headers", "*");
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        response.setHeader("Access-Control-Max-Age", "180");
        
        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}