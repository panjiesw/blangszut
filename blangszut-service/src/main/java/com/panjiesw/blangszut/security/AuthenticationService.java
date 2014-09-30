package com.panjiesw.blangszut.security;

import com.panjiesw.blangszut.persistence.entities.User;
import com.panjiesw.blangszut.persistence.repositories.UserRepository;
import com.panjiesw.blangszut.security.domains.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("authService")
public class AuthenticationService implements UserDetailsService {

	@Qualifier("userRepository")
	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null)
			throw new UsernameNotFoundException(String.format("User not found for %s", username));
		return AuthUser.fromUser(user);
	}
}
