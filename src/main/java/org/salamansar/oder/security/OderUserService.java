package org.salamansar.oder.security;

import org.salamansar.oder.core.domain.User;

/**
 *
 * @author Salamansar
 */
public interface OderUserService extends org.springframework.security.core.userdetails.UserDetailsService {
	
	public User getCurrentUser();

}
