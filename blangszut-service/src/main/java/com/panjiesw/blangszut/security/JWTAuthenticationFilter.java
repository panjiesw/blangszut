package com.panjiesw.blangszut.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

public class JWTAuthenticationFilter extends GenericFilterBean {

	private static final Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

	public static final String HEADER_SECURITY_TOKEN = "Authorization";
	public static final String AUTH_BEARER = "Bearer";

	private final UserDetailsService userDetailsService;
	private final AuthenticationManager authenticationManager;

	public JWTAuthenticationFilter(UserDetailsService userDetailsService, AuthenticationManager authenticationManager) {
		this.userDetailsService = userDetailsService;
		this.authenticationManager = authenticationManager;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {
		HttpServletRequest httpRequest = this.getAsHttpRequest(request);

		String authToken = this.extractToken(httpRequest);
		if (authToken != null) {
			try {
				SignedJWT signedJWT = SignedJWT.parse(authToken);
				JWSVerifier verifier = new MACVerifier("Walolo");
				if (signedJWT.verify(verifier)) {
					UserDetails userDetails = userDetailsService.loadUserByUsername(
						signedJWT.getJWTClaimsSet().getSubject());
					UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
						userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
					token.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
					Authentication authentication = authenticationManager.authenticate(token);
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			} catch (ParseException e) {
				logger.error("Error parsing JWT", e);
			} catch (JOSEException e) {
				logger.error("Error verifying JWT", e);
			}
		}
	}

	private HttpServletRequest getAsHttpRequest(ServletRequest request) {
		if (!(request instanceof HttpServletRequest)) {
			throw new RuntimeException("Expecting an HTTP request");
		}

		return (HttpServletRequest) request;
	}

	private String extractToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(HEADER_SECURITY_TOKEN);
		String token = null;

		// Get token from header
		if (bearerToken != null && !bearerToken.isEmpty()) {
			String[] exploded = bearerToken.split(": ", 2);
			if (exploded.length == 2 && exploded[0].equals(AUTH_BEARER))
				token = exploded[1];
		}

		// If not found, get from request parameter
		if (token == null)
			token = request.getParameter("token");

		return token;
	}
}
