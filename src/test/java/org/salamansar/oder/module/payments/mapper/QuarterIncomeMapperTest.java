package org.salamansar.oder.module.payments.mapper;

import java.math.BigDecimal;
import org.envbuild.generator.RandomGenerator;
import static org.junit.Assert.*;
import org.junit.Test;
import org.mapstruct.factory.Mappers;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
import org.salamansar.oder.core.domain.QuarterIncome;
import org.salamansar.oder.module.payments.dto.QuarterIncomeDto;

/**
 *
 * @author Salamansar
 */
public class QuarterIncomeMapperTest {
	private QuarterIncomeMapper mapper = Mappers.getMapper(QuarterIncomeMapper.class);
	
	@Test
	public void mapToDto() {
		QuarterIncome income = new QuarterIncome(new PaymentPeriod(2018, Quarter.YEAR), BigDecimal.valueOf(100L));
		
		QuarterIncomeDto result = mapper.mapToDto(income);
		
		assertNotNull(result);
		assertEquals(income.getPeriod(), result.getPeriod());
		assertTrue(income.getIncomeAmount().compareTo(result.getIncomeAmount()) == 0);
	}
	
}
