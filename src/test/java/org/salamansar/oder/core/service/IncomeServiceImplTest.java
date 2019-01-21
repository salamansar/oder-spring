package org.salamansar.oder.core.service;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import org.envbuild.generator.RandomGenerator;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.salamansar.oder.core.component.QuarterIncomeMapStartegyFactory;
import org.salamansar.oder.core.component.QuarterIncomeMapStrategy;
import org.salamansar.oder.core.dao.IncomeDao;
import org.salamansar.oder.core.domain.Income;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
import org.salamansar.oder.core.domain.QuarterIncome;
import org.salamansar.oder.core.domain.User;

/**
 *
 * @author Salamansar
 */
@RunWith(MockitoJUnitRunner.class)
public class IncomeServiceImplTest {
	@Mock
	private IncomeDao incomeDao;
	@Mock
	private QuarterIncomeMapStartegyFactory quarterInocomeMapFactory;
	@Mock
	private QuarterIncomeMapStrategy mapSrategy;
	@InjectMocks
	private IncomeServiceImpl service;
	private RandomGenerator generator = new RandomGenerator();
	private User user;
	
	@Before
	public void setUp() {
		user = generator.generate(User.class);
	}
	

	@Test
	public void testGetAllIncomes() {
		Income income = generator.generate(Income.class);
		when(incomeDao.findIncomeByUserOrderByIncomeDateDesc(same(user)))
				.thenReturn(Arrays.asList(income));
		
		List<Income> result = service.getAllIncomes(user);
		
		assertNotNull(result);
		assertEquals(1, result.size());
		assertSame(income, result.get(0));
	}

	@Test
	public void testAddIncome() {
		Income income = generator.generate(Income.class);
		when(incomeDao.save(same(income))).thenReturn(income);
		
		service.addIncome(income);
		
		assertNull(income.getId());
	}

	@Test
	public void testFindIncomes() {
		Income income = generator.generate(Income.class);
		when(incomeDao.findIncomeByUserAndIncomeDateBetween(same(user), 
				eq(LocalDate.of(2018, Month.JULY, 1)), 
				eq(LocalDate.of(2018, Month.SEPTEMBER, 30))))
				.thenReturn(Arrays.asList(income));
		
		List<Income> result = service.findIncomes(user, new PaymentPeriod(2018, Quarter.III));
		
		assertNotNull(result);
		assertEquals(1, result.size());
		assertSame(income, result.get(0));
	}
	
	@Test
	public void testFindQuarterIncomes() {
		Income income = generator.generate(Income.class);
		QuarterIncome quarterIncome = generator.generate(QuarterIncome.class);
		when(incomeDao.findIncomeByUserAndIncomeDateBetween(same(user), 
				eq(LocalDate.of(2018, Month.JULY, 1)), 
				eq(LocalDate.of(2018, Month.SEPTEMBER, 30))))
				.thenReturn(Arrays.asList(income));
		when(quarterInocomeMapFactory.getStrategy(eq(new PaymentPeriod(2018, Quarter.III)), eq(true)))
				.thenReturn(mapSrategy);
		when(mapSrategy.map(eq(Arrays.asList(income)))).thenReturn(Arrays.asList(quarterIncome));
		
		List<QuarterIncome> result = service.findQuarterIncomes(user, new PaymentPeriod(2018, Quarter.III), true);
		
		assertNotNull(result);
		assertEquals(1, result.size());
		assertSame(quarterIncome, result.get(0));
	}
	
}
