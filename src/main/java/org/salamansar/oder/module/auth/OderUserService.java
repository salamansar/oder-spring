package org.salamansar.oder.module.auth;

import org.salamansar.oder.core.domain.User;

/**
 *
 * @author Salamansar
 */
public interface OderUserService extends org.springframework.security.core.userdetails.UserDetailsService {
	
	public User getCurrentUser();

}
