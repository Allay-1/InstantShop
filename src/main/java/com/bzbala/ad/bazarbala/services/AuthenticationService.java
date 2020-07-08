package com.bzbala.ad.bazarbala.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AuthenticationService {

	public boolean authenticateUserSer(String userId, String pwdToAuth, String userType);

	public UserDetails loadUserByUsername(String phoneNumber, String userType) throws UsernameNotFoundException;
}
