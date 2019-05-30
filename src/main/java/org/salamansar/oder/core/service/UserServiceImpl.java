package org.salamansar.oder.core.service;

import java.util.Optional;
import org.salamansar.oder.core.dao.UserDao;
import org.salamansar.oder.core.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Salamansar
 */
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	@Override
	public Optional<User> getUserByLogin(String login) {
		return Optional.ofNullable(
				userDao.findUserByLogin(login.toLowerCase().trim())
		);
	}
	
}
