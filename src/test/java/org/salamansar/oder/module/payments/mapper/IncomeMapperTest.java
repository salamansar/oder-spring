package org.salamansar.oder.module.payments.mapper;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.Date;
import org.envbuild.generator.RandomGenerator;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mapstruct.factory.Mappers;
import org.salamansar.oder.core.domain.Income;
import org.salamansar.oder.module.payments.dto.IncomeDto;

/**
 *
 * @author Salamansar
 */
public class IncomeMapperTest {
	
	private IncomeMapper mapper = Mappers.getMapper(IncomeMapper.class);
	private RandomGenerator generator = new RandomGenerator();
	
	@Test
	public void mapIncome() {
		Income income =  generator.generate(Income.class);
		LocalDate date = LocalDate.of(2018, Month.MARCH, 1);
		income.setIncomeDate(date);
		
		IncomeDto result = mapper.mapToDto(income);
		
		assertNotNull(result);
		assertNotNull(result.getAmount());
		assertTrue(income.getAmount().compareTo(result.getAmount()) == 0);
		assertNotNull(result.getDescription());
		assertEquals(income.getDescription(), result.getDescription());
		assertNotNull(result.getDocumentNumber());
		assertEquals(income.getDocumentNumber(), result.getDocumentNumber());
		assertNotNull(result.getId());
		assertEquals(income.getId(), result.getId());
		assertNotNull(result.getIncomeDate());
		assertEquals(new Date(date.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()), result.getIncomeDate());
	}
	
	@Test
	public void mapIncomeWithEmptyDate() {
		Income income = generator.generate(Income.class);

		IncomeDto result = mapper.mapToDto(income);

		assertNotNull(result);
		assertNull(result.getIncomeDate());
	}
	
	@Test
	public void mapNullIncome() {

		IncomeDto result = mapper.mapToDto(null);

		assertNull(result);
	}
	
	
}
