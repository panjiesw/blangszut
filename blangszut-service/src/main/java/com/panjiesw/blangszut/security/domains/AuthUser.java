package com.panjiesw.blangszut.security.domains;

import com.panjiesw.blangszut.persistence.entities.User;
import com.panjiesw.blangszut.persistence.entities.enums.UserStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AuthUser implements UserDetails {

	private static final long serialVersionUID = 4374501457522595709L;
	private String username;
	private String password;
	private boolean enabled;
	private List<Authority> authorities;

	public AuthUser(User user) {
		username = user.getUsername();
		password = user.getPassword();
		enabled = user.getStatus() == UserStatus.ACTIVE;

		authorities = user.getRoleList().stream().map(r -> new Authority(r.getName())).collect(Collectors.toList());
	}

	public static AuthUser fromUser(User user) {
		return new AuthUser(user);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return enabled;
	}

	@Override
	public boolean isAccountNonLocked() {
		return enabled;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return enabled;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
}
