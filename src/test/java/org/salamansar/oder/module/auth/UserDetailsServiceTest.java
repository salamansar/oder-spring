package org.salamansar.oder.module.auth;

import java.util.Optional;
import org.envbuild.generator.RandomGenerator;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.salamansar.oder.core.domain.User;
import org.salamansar.oder.core.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *
 * @author Salamansar
 */
@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceTest {
	@Mock
	private UserService userService;
	@InjectMocks
	private UserDetailsService service;
	private RandomGenerator randomGenerator = new RandomGenerator();
	private User user;
	
	@Before
	public void setUp() {
		user = randomGenerator.generate(User.class);
		when(userService.getUserByLogin(anyString()))
				.thenReturn(Optional.empty());
		when(userService.getUserByLogin(eq(user.getLogin())))
				.thenReturn(Optional.of(user));
	}
	

	@Test
	public void foundUser() {
		UserDetails result = service.loadUserByUsername(user.getLogin());
		
		assertNotNull(result);
		assertTrue(result instanceof UserDetailsWrapper);
		UserDetailsWrapper wrapper = (UserDetailsWrapper) result;
		assertEquals(user.getLogin(), wrapper.getUsername());
		assertEquals("{noop}" + user.getPasswordHash(), wrapper.getPassword());
		assertEquals(1, wrapper.getAuthorities().size());
		assertTrue(wrapper.isAccountNonExpired());
		assertTrue(wrapper.isAccountNonLocked());
		assertTrue(wrapper.isCredentialsNonExpired());
		assertTrue(wrapper.isEnabled());
	}
	
	@Test(expected = UsernameNotFoundException.class)
	public void notFoundUser() {
		service.loadUserByUsername("otherLogin");
	}
	
}
