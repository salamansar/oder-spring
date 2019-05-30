package org.salamansar.oder.core.service;

import java.util.Optional;
import org.envbuild.generator.RandomGenerator;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.salamansar.oder.core.dao.UserDao;
import org.salamansar.oder.core.domain.User;

/**
 *
 * @author Salamansar
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
	@Mock
	private UserDao userDao;
	@InjectMocks
	private UserServiceImpl service;
	private RandomGenerator randomGenerator = new RandomGenerator();
	private User user;
	
	@Before
	public void setUp() {
		user = randomGenerator.generate(User.class);
		when(userDao.findUserByLogin(eq(user.getLogin())))
				.thenReturn(user);
	}
	

	@Test
	public void findingUserByLogin() {
		Optional<User> result = service.getUserByLogin("  " + user.getLogin().toUpperCase());
		
		assertTrue(result.isPresent());
		assertSame(user, result.get());
	}
	
	@Test
	public void notFoundUserByLogin() {
		Optional<User> result = service.getUserByLogin("test");
		
		assertFalse(result.isPresent());
	}
	
}
