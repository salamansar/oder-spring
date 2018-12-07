package org.salamansar.oder.core.service;

import java.util.Arrays;
import java.util.List;
import org.envbuild.generator.RandomGenerator;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.salamansar.oder.core.component.IncomeMapStrategy;
import org.salamansar.oder.core.component.IncomeMapStrategyFactory;
import org.salamansar.oder.core.domain.Income;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
import org.salamansar.oder.core.domain.Tax;
import org.salamansar.oder.core.domain.TaxCalculationSettings;
import org.salamansar.oder.core.domain.User;

/**
 *
 * @author Salamansar
 */
@RunWith(MockitoJUnitRunner.class)
public class IncomesTaxCalculatorImplTest {
	@Mock
	private IncomeService incomesService;
	@Mock
	private IncomeMapStrategyFactory strategyFactory;
	@Mock
	private IncomeMapStrategy strategy;
	@InjectMocks
	private IncomesTaxCalculatorImpl calculator = new IncomesTaxCalculatorImpl();
	private RandomGenerator generator = new RandomGenerator();
	
	@Test
	public void calculateIncomes() {
		User user = generator.generate(User.class);
		PaymentPeriod period = new PaymentPeriod(2018, Quarter.III);
		Income income = generator.generate(Income.class);
		Tax tax = generator.generate(Tax.class, period);
		TaxCalculationSettings settings = new TaxCalculationSettings();
		when(incomesService.findIncomes(same(user), same(period)))
				.thenReturn(Arrays.asList(income));
		when(strategyFactory.getStrategy(same(period), same(settings)))
				.thenReturn(strategy);
		when(strategy.map(eq(Arrays.asList(income))))
				.thenReturn(Arrays.asList(tax));
		
		List<Tax> result = calculator.calculateIncomeTaxes(user, period, settings);
		
		assertNotNull(result);
		assertEquals(1, result.size());
		assertSame(tax, result.get(0));
	}
	
}
