package org.salamansar.oder.module.profile.dto;

import org.salamansar.oder.module.profile.dto.UserDto;
import org.envbuild.generator.RandomGenerator;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Salamansar
 */
public class UserDtoTest {
	private RandomGenerator generator = new RandomGenerator();
	
	@Test
	public void allFieldsFilledFullName() {
		UserDto user = generator.generate(UserDto.class);
		String expected = String.join(" ", user.getLastName(), user.getFirstName(), user.getMiddleName());
		
		assertEquals(expected, user.getFullName());
	}
	
	@Test
	public void missedLastNameForFullName() {
		UserDto user = generator.generate(UserDto.class);
		user.setLastName(null);
		String expected = String.join(" ", user.getFirstName(), user.getMiddleName());

		assertEquals(expected, user.getFullName());
	}
	
	@Test
	public void missedFirstNameForFullName() {
		UserDto user = generator.generate(UserDto.class);
		user.setFirstName(null);
		String expected = String.join(" ", user.getLastName(), user.getMiddleName());

		assertEquals(expected, user.getFullName());
	}
	
	@Test
	public void missedMiddleNameForFullName() {
		UserDto user = generator.generate(UserDto.class);
		user.setMiddleName(null);
		String expected = String.join(" ", user.getLastName(), user.getFirstName());

		assertEquals(expected, user.getFullName());
	}
	
	@Test
	public void missedFirstNameAndMiddleNameForFullName() {
		UserDto user = generator.generate(UserDto.class);
		user.setFirstName(null);
		user.setMiddleName(null);
		String expected = user.getLastName();

		assertEquals(expected, user.getFullName());
	}
	
	@Test
	public void missedLastNameAndMiddleNameForFullName() {
		UserDto user = generator.generate(UserDto.class);
		user.setLastName(null);
		user.setMiddleName(null);
		String expected = user.getFirstName();

		assertEquals(expected, user.getFullName());
	}
	
	@Test
	public void missedLastNameAndFirstNameForFullName() {
		UserDto user = generator.generate(UserDto.class);
		user.setLastName(null);
		user.setFirstName(null);
		String expected = user.getMiddleName();

		assertEquals(expected, user.getFullName());
	}
	
	@Test
	public void missedAllNamesForFullName() {
		UserDto user = new UserDto();

		assertEquals("", user.getFullName());
	}
}
