package org.salamansar.oder.module.profile.mapper;

import org.mapstruct.Mapper;
import org.salamansar.oder.core.domain.User;
import org.salamansar.oder.module.profile.dto.UserDto;

/**
 *
 * @author Salamansar
 */
@Mapper
public interface UserMapper {
	
	UserDto mapToDto(User user);
}
