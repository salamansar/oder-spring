package org.salamansar.oder.module.profile.mapper;

import org.envbuild.generator.RandomGenerator;
import static org.junit.Assert.*;
import org.junit.Test;
import org.mapstruct.factory.Mappers;
import org.salamansar.oder.core.domain.User;
import org.salamansar.oder.module.profile.dto.UserDto;

/**
 *
 * @author Salamansar
 */
public class UserMapperTest {
	private UserMapper mapper = Mappers.getMapper(UserMapper.class);
	private RandomGenerator generator = new RandomGenerator();
	
	@Test
	public void mapToDto() {
		User srcUser = generator.generate(User.class);
		
		UserDto dto = mapper.mapToDto(srcUser);
		
		assertNotNull(dto);
		assertEquals(srcUser.getFirstName(), dto.getFirstName());
		assertEquals(srcUser.getLastName(), dto.getLastName());
		assertEquals(srcUser.getMiddleName(), dto.getMiddleName());
		assertEquals(srcUser.getLogin(), dto.getLogin());
	}
	
}
