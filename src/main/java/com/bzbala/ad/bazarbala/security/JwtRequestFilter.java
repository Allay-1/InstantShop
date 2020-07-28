package com.bzbala.ad.bazarbala.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bzbala.ad.bazarbala.dao.PersonalInfo;
import com.bzbala.ad.bazarbala.services.AuthenticationService;
import com.bzbala.ad.bazarbala.util.JwtUtil;



@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	AuthenticationService authenticationServiceObj;
	
    @Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		final String authorizationHeader = request.getHeader("Authorization");
		final String userType = request.getHeader("User_Type");

		// ResponseEntity<UserDetails> userDetails = null;
		String username = null;
		String jwt = null;
		Map<String, String> urlParameters = new HashMap<>();

		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			jwt = authorizationHeader.substring(7);
			try {

				username = jwtUtil.extractUsername(jwt);

			} catch (Exception e) {
				e.printStackTrace();
			}

			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				PersonalInfo personalInfo = authenticationServiceObj.loadUserByUsername(username, userType,false);
				UserDetails userDetails = personalInfo.getUserDetail();
				// if token is valid configure Spring Security to manually set
				// authentication
				if (jwtUtil.validateToken(jwt, userDetails)) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					// After setting the Authentication in the context, we specify
					// that the current user is authenticated. So it passes the
					// Spring Security Configurations successfully.
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}
			urlParameters.put("username", username);
		}

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, X-Custom-Header");

		chain.doFilter(request, response);
	}

}
