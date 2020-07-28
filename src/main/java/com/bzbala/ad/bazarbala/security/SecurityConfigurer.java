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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



@Configuration
@EnableWebSecurity(debug = true) 
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService myUserDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;
  //
	String[] publicUrls = new String[] {"/removeBin","/genrateBin","/user/logout","/customer/addItem","/customer/getItems", "/destroySession",
			"/user/authAndCreateToken", "/user/signUpSupplier", "/user/signUPCustomer", "/supplier/availability","/product/getAllProduct","/product/getSupplierProduct/**","/user/genrate/passwordToken", "/user/genrate/password"};
	
	String[]  customerUrls= new String[] {"/customer/updateOrderRequest","/customer/findCustomerOrder"};
	
	String[] supplierUrls = new String[] {"/supplier/deleteProduct/**", "/supplier/categoryDetail/**",
			"/supplier/download/*", "/supplier/upload/*", "/supplier/uploadProduct/*", "/supplier/saveProduct"
			,"/supplier/findOrderBySupplierId"};
	
//	public String[] getAdminUrl(String[] supplierUrls, String[] customerUrls) {
//		List<String> adminlist = new ArrayList<>(Arrays.asList(supplierUrls));
//		adminlist.addAll(Arrays.asList(customerUrls));
//		String[] adminUrl = (String[]) adminlist.toArray();
//		return adminUrl;
//	}

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
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers(customerUrls).hasRole("CUSTOMER")
                .antMatchers(supplierUrls).hasRole("SUPPLIER")
                .antMatchers("/acmin").hasAnyRole("SUPPLIER","CUSTOMER")
                .antMatchers(publicUrls).permitAll()
				.and().headers().frameOptions()
				.sameOrigin().httpStrictTransportSecurity().disable().and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")).invalidateHttpSession(true) 
				.deleteCookies("JSESSIONID").and().exceptionHandling().accessDeniedPage("/403");
		;
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

	}
	
	

	private CookieCsrfTokenRepository csrfRepo() {
		CookieCsrfTokenRepository repo = new CookieCsrfTokenRepository();
		repo.setCookieHttpOnly(false);
		return repo;
	}

}
