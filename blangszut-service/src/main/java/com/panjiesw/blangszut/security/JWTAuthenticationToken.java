package com.panjiesw.blangszut.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.text.ParseException;
import java.util.Collection;

public class JWTAuthenticationToken extends AbstractAuthenticationToken {

	private Object principal;
	private Object details;
	private Collection<GrantedAuthority> authorities;

	public JWTAuthenticationToken(String token) {
		super(null);
		try {
			SignedJWT signed = SignedJWT.parse(token);
			JWSVerifier verifier = new MACVerifier("walolo");
			super.setAuthenticated(signed.verify(verifier));
			this.principal = signed.getJWTClaimsSet().getSubject();
		} catch (ParseException | JOSEException e) {
			super.setAuthenticated(false);
		}
	}

	private void setDetailAuthorities() {
		String username = principal.toString();
	}

	@Override
	public Object getCredentials() {
		return "";
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return super.getAuthorities();
	}
}
