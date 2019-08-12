package org.salamansar.oder.module.common;

import java.lang.reflect.Method;
import java.util.Optional;
import org.salamansar.oder.core.domain.User;
import org.salamansar.oder.module.profile.mapper.UserMapper;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

/**
 *
 * @author Salamansar
 */
@Component
public class FillUserInfoAdvice implements AfterReturningAdvice  {
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		findByClass(args, User.class).ifPresent(user -> {
			findByClass(args, Model.class).ifPresent(model -> {
				model.addAttribute(CommonFormAttribute.USER.attributeName(), userMapper.mapToDto(user));
			});
		});
	}
	
	private <T> Optional<T> findByClass(Object[] args, Class<T> clazz) {
		for (Object arg : args) {
			if (arg != null && clazz.isAssignableFrom(arg.getClass())) {
				return Optional.of((T) arg);
			}
		}
		return Optional.empty();
	}
	
}
