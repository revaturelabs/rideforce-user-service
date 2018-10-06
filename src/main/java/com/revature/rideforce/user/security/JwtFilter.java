package com.revature.rideforce.user.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * JWT filter for getting tokens and setting the SecurityContextHolder, setting a user as logged in<p>
 * <strong>Member Variables</strong><br>
 * {@linkplain LoginTokenProvider} tokenProvider
 * @author clpeng
 * @since Iteration1 10/01/2018
 *
 */
public class JwtFilter extends OncePerRequestFilter {
    private LoginTokenProvider tokenProvider;

    /**Constructor
     * @param tokenProvider for Login 
     */
    
    public JwtFilter(LoginTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    /* (non-Javadoc)
     * @see org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = tokenProvider.extractToken(request);
        if (token != null) {
            Authentication auth = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }
}