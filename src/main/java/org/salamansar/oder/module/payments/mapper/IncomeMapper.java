package org.salamansar.oder.module.payments.mapper;

import org.mapstruct.Mapper;
import org.salamansar.oder.core.domain.Income;
import org.salamansar.oder.module.payments.dto.IncomeDto;

/**
 *
 * @author Salamansar
 */
@Mapper
public interface IncomeMapper {
	
	IncomeDto mapToDto(Income income);
	
	Income mapFromDto(IncomeDto income);
	
}
