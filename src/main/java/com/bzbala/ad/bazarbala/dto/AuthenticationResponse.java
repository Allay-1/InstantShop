package com.bzbala.ad.bazarbala.dto;

public class AuthenticationResponse {
	
	
	 private final String jwt;

	    public AuthenticationResponse(String jwt) {
	        this.jwt = jwt;
	    }

	    public String getJwt() {
	        return jwt;
	    }

}
