package org.salamansar.oder.module.common;

import java.lang.reflect.Method;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.salamansar.oder.core.domain.User;
import org.salamansar.oder.module.profile.dto.UserDto;
import org.salamansar.oder.module.profile.mapper.UserMapper;
import org.springframework.ui.Model;

/**
 *
 * @author Salamansar
 */
@RunWith(MockitoJUnitRunner.class)
public class FillUserInfoAdviceTest {
	@Mock
	private UserMapper userMapper;
	@InjectMocks
	private FillUserInfoAdvice advice;
	@Mock
	private Model model;
	private Method method;
	
	@Before
	public void setUp() {
		when(userMapper.mapToDto(any(User.class))).thenReturn(new UserDto());
		method = this.getClass().getMethods()[0];
	}
	
	@Test
	public void populateWithUserInfo() throws Throwable {
		Object[] args = new Object[] { 1L, model, new User() };
		
		advice.afterReturning(20L, method, args, this);
		
		verify(model).addAttribute(eq("user"), any(UserDto.class));
	}
	
	@Test
	public void missingUser() throws Throwable {
		Object[] args = new Object[] { 1L, model, new Object() };
		
		advice.afterReturning(20L, method, args, this);
		
		verify(model, never()).addAttribute(anyString(), any(UserDto.class));
	}
	
	@Test
	public void missingModel() throws Throwable {
		Object[] args = new Object[]{1L, new Object(), new User()};

		advice.afterReturning(20L, method, args, this);

		verify(model, never()).addAttribute(anyString(), any(UserDto.class));
	}
	
	@Test
	public void nullObjectInParams() throws Throwable {
		Object[] args = new Object[]{null, model, new User()};

		advice.afterReturning(20L, method, args, this);

		verify(model).addAttribute(eq("user"), any(UserDto.class));
	}
}
