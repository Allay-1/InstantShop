package com.bzbala.ad.bazarbala.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AuthenticationService {
	
	
	public boolean authenticateUserSer(String userId, String pwdToAuth);
	public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException;
}
