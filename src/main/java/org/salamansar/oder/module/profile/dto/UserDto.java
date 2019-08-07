package org.salamansar.oder.module.profile.dto;

import java.util.stream.Stream;
import lombok.Data;

/**
 *
 * @author Salamansar
 */
@Data
public class UserDto {
	private String login;
	private String lastName;
	private String firstName;
	private String middleName;
	
	public String getFullName() {
		String lastNameStr = lastName == null ? "" : lastName;
		return Stream.of(firstName, middleName)
				.reduce(lastNameStr, (accum, str) -> 
						str == null 
								? accum 
								: accum.isEmpty() 
										? str 
										: String.join(" ", accum, str)
						);
	}
	
}
