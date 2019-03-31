package org.salamansar.oder.core.service;

import java.math.BigDecimal;
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
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
import org.salamansar.oder.core.domain.Tax;
import org.salamansar.oder.core.domain.TaxCalculationSettings;
import org.salamansar.oder.core.domain.User;
import org.salamansar.oder.core.component.TaxAmountCalculator;
import org.salamansar.oder.core.domain.QuarterIncome;
import org.salamansar.oder.core.domain.TaxCategory;

/**
 *
 * @author Salamansar
 */
@RunWith(MockitoJUnitRunner.class)
public class IncomesTaxCalculatorImplTest {
	@Mock
	private IncomeService incomesService;
	@Mock
	private TaxAmountCalculator amountCalculator;
	@InjectMocks
	private IncomesTaxCalculatorImpl calculator;
	private RandomGenerator generator = new RandomGenerator();
	
	@Test
	public void calculateIncomes() {
		User user = generator.generate(User.class);
		PaymentPeriod period = new PaymentPeriod(2018, Quarter.III);
		QuarterIncome income1 = generator.generate(QuarterIncome.class, new PaymentPeriod(2018, Quarter.I));
		QuarterIncome income2 = generator.generate(QuarterIncome.class, new PaymentPeriod(2018, Quarter.III));
		TaxCalculationSettings settings = new TaxCalculationSettings().splitByQuants(true);
		when(incomesService.findQuarterIncomes(same(user), same(period), eq(true)))
				.thenReturn(Arrays.asList(income1, income2));
		when(amountCalculator.calculateTax(same(income1)))
				.thenReturn(BigDecimal.valueOf(100));
		
		List<Tax> result = calculator.calculateIncomeTaxes(user, period, settings);
		
		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals(TaxCategory.INCOME_TAX, result.get(0).getCatgory());
		assertEquals(new PaymentPeriod(2018, Quarter.I), result.get(0).getPeriod());
		assertNotNull(result.get(0).getPayment());
		assertTrue(BigDecimal.valueOf(100).compareTo(result.get(0).getPayment()) == 0);
		assertEquals(TaxCategory.INCOME_TAX, result.get(1).getCatgory());
		assertEquals(new PaymentPeriod(2018, Quarter.III), result.get(1).getPeriod());
		assertNull(result.get(1).getPayment());
	}
	
}
