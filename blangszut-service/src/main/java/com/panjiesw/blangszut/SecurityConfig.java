package com.panjiesw.blangszut;

import com.panjiesw.blangszut.security.AuthenticationService;
import com.panjiesw.blangszut.security.JWTConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultSecurityFilterChain;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class SecurityConfig {

//	@Bean
//	UserDetailsService authService() {
//		return new AuthenticationService();
//	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationEntryPoint restEntryPoint() {
		return new RestEntryPoint();
	}
}

@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
class BlangszutSecurityConfigurer extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService authService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	AuthenticationEntryPoint restEntryPoint;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(
			SessionCreationPolicy.STATELESS);
		http.exceptionHandling().authenticationEntryPoint(restEntryPoint);

		http.authorizeRequests()
			.antMatchers("/partials/**", "/lib/**").permitAll()
			.antMatchers("/", "/admin/**", "/login").permitAll()
			.antMatchers("/api/**").hasRole("USER")
			.anyRequest().authenticated()
			.and().formLogin().loginPage("/login").permitAll();

		SecurityConfigurer<DefaultSecurityFilterChain, HttpSecurity> securityConfigurerAdapter = new JWTConfigurer(
			userDetailsServiceBean(), authenticationManagerBean());
		http.apply(securityConfigurerAdapter);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(authService)
			.passwordEncoder(passwordEncoder);
	}

	@Bean(name = "authenticationManager")
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}

class RestEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
		throws IOException, ServletException
	{
		response.sendError(
			HttpServletResponse.SC_UNAUTHORIZED,
			"Unauthorized: Authentication token was either missing or invalid.");
	}

}
