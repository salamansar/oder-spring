package org.salamansar.oder.module.auth;

import java.util.Arrays;
import java.util.Collection;
import org.salamansar.oder.core.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Salamansar
 */
public class UserDetailsWrapper implements UserDetails {
	private User targetUser;

	public UserDetailsWrapper(User targetUser) {
		this.targetUser = targetUser;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(() -> "USER");
	}

	@Override
	public String getPassword() {
		//todo: use some cipher
		return "{noop}" + targetUser.getPasswordHash();
	}

	@Override
	public String getUsername() {
		return targetUser.getLogin();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	
}
