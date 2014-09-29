package com.panjiesw.blangszut.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private static final Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

	public static final String HEADER_SECURITY_TOKEN = "Authorization";
	public static final String AUTH_BEARER = "Bearer";

	public JWTAuthenticationFilter(String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
		super.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(defaultFilterProcessesUrl));
		setAuthenticationManager(new NoOpAuthenticationManager());
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
		String bearerToken = request.getHeader(HEADER_SECURITY_TOKEN);
		if (bearerToken == null || bearerToken.isEmpty())
			return null;
		String[] exploded = bearerToken.split(": ", 2);
		if (exploded.length != 2 && !exploded[0].equals(AUTH_BEARER))
			return null;

		return null;
	}
}

class NoOpAuthenticationManager implements AuthenticationManager {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		return authentication;
	}
}
