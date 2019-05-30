package org.salamansar.oder.core.service;

import java.util.Optional;
import org.envbuild.environment.DbEnvironmentBuilder;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.salamansar.oder.core.AbstractCoreIntegrationTest;
import org.salamansar.oder.core.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Salamansar
 */
@Transactional
public class UserServiceImplIT extends AbstractCoreIntegrationTest {

	@Autowired
	private UserService userService;
	@Autowired
	private DbEnvironmentBuilder envBuilder;
	private User user;
	
	@Before
	public void setUp() {
		envBuilder.newBuild()
				.createObject(User.class).alias("user");
		user = envBuilder.getEnvironment().getByAlias("user");
	}
	
	
	@Test
	public void findUser() {
		Optional<User> result = userService.getUserByLogin("  " + user.getLogin().toUpperCase() + "  ");
	
		assertTrue(result.isPresent());
		assertEquals(user.getId(), result.get().getId());
	}
	
	@Test
	public void notFoundUser() {
		Optional<User> result = userService.getUserByLogin("otherLogin");
	
		assertFalse(result.isPresent());
	}
	
	
}
