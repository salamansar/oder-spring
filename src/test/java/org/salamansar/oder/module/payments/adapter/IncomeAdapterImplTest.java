package org.salamansar.oder.module.payments.adapter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.envbuild.generator.RandomGenerator;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.salamansar.oder.core.domain.Income;
import org.salamansar.oder.core.domain.QuarterIncome;
import org.salamansar.oder.core.domain.User;
import org.salamansar.oder.core.service.IncomeService;
import org.salamansar.oder.module.payments.dto.IncomeDto;
import org.salamansar.oder.module.payments.dto.QuarterIncomeDto;
import org.salamansar.oder.module.payments.mapper.IncomeMapper;
import org.salamansar.oder.module.payments.mapper.QuarterIncomeMapper;

/**
 *
 * @author Salamansar
 */
@RunWith(MockitoJUnitRunner.class)
public class IncomeAdapterImplTest {
	@Mock
	private IncomeService incomeService;
	@Mock
	private IncomeMapper incomeMapper;
	@Mock
	private QuarterIncomeMapper quarterIncomeMapper;
	@InjectMocks
	private IncomeAdapterImpl adapter;
	private RandomGenerator generator = new RandomGenerator();
	private User user;
	
	@Before
	public void setUp() {
		user = generator.generate(User.class);
	}

	@Test
	public void getAllIncomes() {
		Income domain = generator.generate(Income.class, user);
		when(incomeService.getAllIncomes(eq(user)))
				.thenReturn(Arrays.asList(domain));
		IncomeDto dto = generator.generate(IncomeDto.class);
		when(incomeMapper.mapToDto(same(domain))).thenReturn(dto);
		
		List<IncomeDto> result = adapter.getAllIncomes(user);
		
		assertNotNull(result);
		assertEquals(1, result.size());
		assertSame(dto, result.get(0));
	}
	
	@Test
	public void getAllYearQuarterIncomes() {
		QuarterIncome domain = new QuarterIncome();
		when(incomeService.findAllQuarterIncomes(eq(user), eq(false)))
				.thenReturn(Arrays.asList(domain));
		QuarterIncomeDto dto = new QuarterIncomeDto();
		when(quarterIncomeMapper.mapToDto(same(domain))).thenReturn(dto);

		List<QuarterIncomeDto> result = adapter.getAllYearIncomes(user);

		assertNotNull(result);
		assertEquals(1, result.size());
		assertSame(dto, result.get(0));
	}
	
	@Test
	public void addIncome() {
		IncomeDto dto = generator.generate(IncomeDto.class);
		Income domain = generator.generate(Income.class, user);
		when(incomeMapper.mapFromDto(eq(dto))).thenReturn(domain);
		when(incomeService.addIncome(eq(domain)))
				.thenReturn(100L);
		
		Long result = adapter.addIncome(user, dto);
		
		assertNotNull(result);
		assertEquals(100L, result.longValue());
		assertSame(user, domain.getUser());
	}
	
	@Test
	public void updateIncome() {
		IncomeDto dto = generator.generate(IncomeDto.class);
		Income domain = generator.generate(Income.class, user);
		when(incomeMapper.mapFromDto(eq(dto))).thenReturn(domain);

		adapter.editIncome(user, dto);

		verify(incomeService).updateIncome(same(domain));
		assertSame(user, domain.getUser());
	}
	
	@Test
	public void deleteIncome() {
		adapter.deleteIncome(user, 101L);
		
		verify(incomeService).deleteIncome(eq(101L));
	}
	
	
	@Test
	public void getIncome() {
		IncomeDto dto = generator.generate(IncomeDto.class);
		Income domain = generator.generate(Income.class, user);
		when(incomeMapper.mapToDto(eq(domain))).thenReturn(dto);
		when(incomeService.getIncome(eq(domain.getId()))).thenReturn(Optional.of(domain));
		
		IncomeDto result = adapter.getIncome(user, domain.getId());
		
		assertNotNull(result);
		assertSame(dto, result);
	}
	
	@Test
	public void notFoundIncomeOnGetting() {
		IncomeDto dto = generator.generate(IncomeDto.class);
		Income domain = generator.generate(Income.class, user);
		when(incomeMapper.mapToDto(eq(domain))).thenReturn(dto);
		when(incomeService.getIncome(eq(domain.getId()))).thenReturn(Optional.empty());
		
		IncomeDto result = adapter.getIncome(user, domain.getId());
		
		assertNull(result);
	}
	
	
}
