package org.salamansar.oder.core.service;

import java.util.Optional;
import org.salamansar.oder.core.domain.User;

/**
 *
 * @author Salamansar
 */
public interface UserService {

	Optional<User> getUserByLogin(String login);
}
