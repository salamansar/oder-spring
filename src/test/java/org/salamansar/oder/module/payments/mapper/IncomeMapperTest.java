package org.salamansar.oder.module.payments.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
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
		LocalDate date = LocalDate.of(2018, Month.MARCH, 1);
		Income income =  generator.generate(Income.class, date);
		
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
	
	@Test
	public void mapIncomeDto() {
		LocalDateTime current = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
		IncomeDto income =  generator.generate(IncomeDto.class);
		income.setIncomeDate(new Date(current.toInstant(ZoneOffset.UTC).toEpochMilli()));
		
		Income result = mapper.mapFromDto(income);
		
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
		assertEquals(income.getIncomeDate(), new Date(result.getIncomeDate().atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()));
	}
	
	@Test
	public void mapIncomeDtoWithEmptyDate() {
		IncomeDto income = generator.generate(IncomeDto.class);
		income.setIncomeDate(null);

		Income result = mapper.mapFromDto(income);
		
		assertNotNull(result);
		assertNull(result.getIncomeDate());
	}
	
	@Test
	public void mapNullIncomeDto() {
		Income result = mapper.mapFromDto(null);

		assertNull(result);
	}
	
}
