package org.salamansar.oder.module.payments.mapper;

import org.mapstruct.Mapper;
import org.salamansar.oder.core.domain.QuarterIncome;
import org.salamansar.oder.module.payments.dto.QuarterIncomeDto;

/**
 *
 * @author Salamansar
 */
@Mapper
public interface QuarterIncomeMapper {
	
	QuarterIncomeDto mapToDto(QuarterIncome quarterIncome);
	
}
