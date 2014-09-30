package com.panjiesw.blangszut.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JWTConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	private UserDetailsService userDetailsService;
	private AuthenticationManager authenticationManager;

	public JWTConfigurer(UserDetailsService userDetailsService, AuthenticationManager authenticationManager) {
		this.userDetailsService = userDetailsService;
		this.authenticationManager = authenticationManager;
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		JWTAuthenticationFilter filter = new JWTAuthenticationFilter(userDetailsService, authenticationManager);
		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
	}
}
