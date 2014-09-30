package com.panjiesw.blangszut.security.domains;

import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority {

	private static final long serialVersionUID = 8332797503461995944L;
	private String authority;

	public Authority(String role) {
		authority = role;
	}

	@Override
	public String getAuthority() {
		return authority;
	}
}
