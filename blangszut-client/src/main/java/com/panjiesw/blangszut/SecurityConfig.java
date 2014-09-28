package com.panjiesw.blangszut;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
class SecurityConfig {

	@Configuration
	@Order(Ordered.LOWEST_PRECEDENCE - 100)
	protected static class SigninAuthenticationConfig extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
				.antMatchers("/partials/**", "/lib/**", "/login").permitAll()
				.anyRequest().authenticated();
		}
	}
}
