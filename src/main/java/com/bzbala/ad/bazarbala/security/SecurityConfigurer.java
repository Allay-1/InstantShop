package com.bzbala.ad.bazarbala.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;



@Configuration
@EnableWebSecurity(debug = true) 
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService myUserDetailsService;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	String[] publicUrls = new String[] { "/supplier/download/*","/supplier/upload/*","/supplier/uploadProduct","/supplier/saveProduct","/supplier/getAllProduct","/supplier/getAllProduct/{supplierId}","/supplier/getAllProduct/{supplierId}/**","/destroySession","/genrateBin","/removeBin","/user/authAndCreateToken","/user/signUpSupplier", "/user/signUPCustomer","/supplier/availability","/user/genrate/passwordToken","/user/genrate/password",};
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserDetailsService);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		
	    web.ignoring().antMatchers(publicUrls);
	}
	
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity.cors().and().csrf().csrfTokenRepository(this.csrfRepo()).disable().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests().antMatchers(publicUrls)
				.permitAll().anyRequest().authenticated().and().headers().frameOptions().sameOrigin()
				.httpStrictTransportSecurity().disable();
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

	}
	
	private CookieCsrfTokenRepository csrfRepo()
	{
	    CookieCsrfTokenRepository repo = new CookieCsrfTokenRepository();
	    repo.setCookieHttpOnly(false);
	    return repo;
	}
	
}
