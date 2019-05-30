package org.salamansar.oder.core.dao;

import org.salamansar.oder.core.domain.User;
import org.springframework.data.repository.Repository;

/**
 *
 * @author Salamansar
 */
public interface UserDao extends Repository<User, Long> {

	User findUserByLogin(String login);
}
