package org.salamansar.oder.security;

import java.util.Optional;
import org.salamansar.oder.core.domain.User;
import org.salamansar.oder.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *
 * @author Salamansar
 */
public class UserDetailsService implements OderUserService {
	@Autowired
	private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> possibleUser = userService.getUserByLogin(username);
		User foundUser = possibleUser
				.orElseThrow(() -> new UsernameNotFoundException("User with login \"" + username + "\" not found"));
		
		return new UserDetailsWrapper(foundUser);
	}

	@Override
	public User getCurrentUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetailsWrapper) {
			return ((UserDetailsWrapper) principal).getTargetUser();
		} else {
			throw new AuthenticationServiceException("Wrong auth configuration. User object not found.");
		}
	}
}
