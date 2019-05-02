package org.salamansar.oder.core.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import org.envbuild.environment.DbEnvironmentBuilder;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.salamansar.oder.core.AbstractCoreIntegrationTest;
import org.salamansar.oder.core.domain.Income;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
import org.salamansar.oder.core.domain.QuarterIncome;
import org.salamansar.oder.core.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Salamansar
 */
@Transactional
public class IncomeServiceImplIT extends AbstractCoreIntegrationTest {

	@Autowired
	private IncomeService incomeService;
	@Autowired
	private DbEnvironmentBuilder envBuilder;
	
	private User user;

	@Before
	public void setUp() {
		envBuilder.newBuild()
				.createObject(User.class).asParent().alias("user");
				
		user = envBuilder.getEnvironment().getByAlias("user");
	}

	@Test
	public void addIncome() {
		Income income = new Income();
		income.setAmount(BigDecimal.valueOf(10000));
		income.setDescription("Test description");
		income.setDocumentNumber(658);
		income.setIncomeDate(LocalDate.now());
		income.setUser(user);

		Long newId = incomeService.addIncome(income);

		assertNotNull(newId);
	}
	
	@Test
	public void getIncome() {
		envBuilder.setParent(LocalDate.now())
				.createObject(Income.class).alias("income");
		Income income = envBuilder.getEnvironment().getByAlias("income");
		
		Income result = incomeService.getIncome(income.getId());
		
		assertNotNull(result);		
	}
	

	@Test
	public void getAllIncomes() {
		envBuilder.setParent(LocalDate.now())
				.createObject(Income.class).alias("income1")
				.createObject(Income.class).alias("income2");
		Income income1 = envBuilder.getEnvironment().getByAlias("income1");
		Income income2 = envBuilder.getEnvironment().getByAlias("income2");

		List<Income> incomes = incomeService.getAllIncomes(user);

		assertNotNull(incomes);
		assertEquals(2, incomes.size());
		assertTrue(incomes.stream().anyMatch(e -> e.getId().equals(income1.getId())));
		assertTrue(incomes.stream().anyMatch(e -> e.getId().equals(income2.getId())));
		assertTrue(incomes.stream().allMatch(e -> e.getIncomeDate() != null));
	}

	@Test
	public void getIncomesForQuarter() {
		envBuilder.setParent(LocalDate.of(2018, Month.MAY, 5))
					.createObject(Income.class).alias("income1")
				.setParent(LocalDate.of(2018, Month.JULY, 1))
					.createObject(Income.class).alias("income2")
				.setParent(LocalDate.of(2018, Month.APRIL, 1))
					.createObject(Income.class).alias("income3")
				.setParent(LocalDate.of(2018, Month.JUNE, 30))
					.createObject(Income.class).alias("income4");

		List<Income> result = incomeService.findIncomes(user, new PaymentPeriod(2018, Quarter.II));

		assertNotNull(result);
		assertEquals(3, result.size());
		assertTrue(result.stream().anyMatch(inc -> inc.getIncomeDate().getMonth() == Month.APRIL));
		assertTrue(result.stream().anyMatch(inc -> inc.getIncomeDate().getMonth() == Month.MAY));
		assertTrue(result.stream().anyMatch(inc -> inc.getIncomeDate().getMonth() == Month.JUNE));
		assertFalse(result.stream().anyMatch(inc -> inc.getIncomeDate().getMonth() == Month.JULY));
	}
	
	@Test
	public void getQuarterIncomesForSingleQuarter() {
		envBuilder.setParent(LocalDate.of(2018, Month.FEBRUARY, 5))
					.createObject(Income.class)
				.setParent(LocalDate.of(2018, Month.MARCH, 30))
					.createObject(Income.class)
				.setParent(LocalDate.of(2018, Month.JULY, 1))
					.createObject(Income.class)
				.setParent(LocalDate.of(2018, Month.SEPTEMBER, 30))
					.createObject(Income.class);

		List<QuarterIncome> result = incomeService.findQuarterIncomes(user, new PaymentPeriod(2018, Quarter.II), false);

		assertNotNull(result);
		assertTrue(result.isEmpty());
		
		result = incomeService.findQuarterIncomes(user, new PaymentPeriod(2018, Quarter.I), false);
		
		assertNotNull(result);
		assertEquals(1, result.size());
		assertNotNull(result.get(0).getPeriod());
		assertEquals(new PaymentPeriod(2018, Quarter.I), result.get(0).getPeriod());
		assertNotNull(result.get(0).getIncomeAmount());
	}
	
	@Test
	public void getQuantizedQuarterIncomes() {
		envBuilder.setParent(LocalDate.of(2018, Month.FEBRUARY, 5))
					.createObject(Income.class)
				.setParent(LocalDate.of(2018, Month.MARCH, 30))
					.createObject(Income.class)
				.setParent(LocalDate.of(2018, Month.JULY, 1))
					.createObject(Income.class)
				.setParent(LocalDate.of(2018, Month.SEPTEMBER, 30))
					.createObject(Income.class);

		List<QuarterIncome> result = incomeService.findQuarterIncomes(user, new PaymentPeriod(2018, Quarter.YEAR), true);

		assertNotNull(result);
		assertEquals(2, result.size());
		assertTrue(result.stream().anyMatch(inc -> inc.getPeriod().getQuarter() == Quarter.I));
		assertTrue(result.stream().anyMatch(inc -> inc.getPeriod().getQuarter() == Quarter.III));
		assertTrue(result.stream().allMatch(inc -> inc.getIncomeAmount() != null));
	}
	
	@Test
	public void getSummarizedQuarterIncomes() {
		envBuilder.setParent(LocalDate.of(2018, Month.FEBRUARY, 5))
					.createObject(Income.class)
				.setParent(LocalDate.of(2018, Month.MARCH, 30))
					.createObject(Income.class)
				.setParent(LocalDate.of(2018, Month.JULY, 1))
					.createObject(Income.class)
				.setParent(LocalDate.of(2018, Month.SEPTEMBER, 30))
					.createObject(Income.class);

		List<QuarterIncome> result = incomeService.findQuarterIncomes(user, new PaymentPeriod(2018, Quarter.YEAR), false);

		assertNotNull(result);
		assertEquals(1, result.size());
		assertNotNull(result.get(0).getPeriod());
		assertEquals(new PaymentPeriod(2018, Quarter.YEAR), result.get(0).getPeriod());
		assertNotNull(result.get(0).getIncomeAmount());
	}
	
	@Test
	public void getSummaryYearIncome() {
		envBuilder.setParent(LocalDate.of(2018, Month.FEBRUARY, 5))
					.createObject(Income.class)
				.setParent(LocalDate.of(2018, Month.MARCH, 30))
					.createObject(Income.class)
				.setParent(LocalDate.of(2018, Month.JULY, 1))
					.createObject(Income.class)
				.setParent(LocalDate.of(2018, Month.SEPTEMBER, 30))
					.createObject(Income.class);

		QuarterIncome result = incomeService.findSingleIncome(user, new PaymentPeriod(2018, Quarter.YEAR));

		assertNotNull(result);
		assertNotNull(result.getPeriod());
		assertEquals(new PaymentPeriod(2018, Quarter.YEAR), result.getPeriod());
		assertNotNull(result.getIncomeAmount());
		
		result = incomeService.findSingleIncome(user, new PaymentPeriod(2019, Quarter.YEAR));
		
		assertNull(result);
	}
	
	@Test
	public void findYearsWithIncomes() {
		envBuilder.setParent(LocalDate.of(2018, Month.FEBRUARY, 5))
					.createObject(Income.class)
				.setParent(LocalDate.of(2016, Month.MARCH, 30))
					.createObject(Income.class)
				.setParent(LocalDate.of(2018, Month.JULY, 1))
					.createObject(Income.class);
		
		List<Integer> result = incomeService.findYearsWithIncomes(user);
		
		assertNotNull(result);
		assertEquals(2, result.size());
		assertTrue(result.stream().anyMatch(year -> year.equals(2018)));
		assertTrue(result.stream().anyMatch(year -> year.equals(2016)));
	}
	

}
