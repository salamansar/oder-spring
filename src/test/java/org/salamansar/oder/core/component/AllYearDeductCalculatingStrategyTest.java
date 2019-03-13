package org.salamansar.oder.core.component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.envbuild.generator.RandomGenerator;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.salamansar.oder.core.domain.FixedPayment;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
import org.salamansar.oder.core.domain.TaxCalculationSettings;
import org.salamansar.oder.core.domain.TaxDeduction;
import org.salamansar.oder.core.domain.User;
import org.salamansar.oder.core.service.FixedPaymentService;
import org.salamansar.oder.core.service.OnePercentTaxCalculator;

/**
 *
 * @author Salamansar
 */
@RunWith(MockitoJUnitRunner.class)
public class AllYearDeductCalculatingStrategyTest {
	@Mock
	private FixedPaymentService fixedPaymentService;
	@Mock
	private OnePercentTaxCalculator onePercentCalculator;
	@InjectMocks
	private AllYearDeductCalculatingStrategy strategy;
	private RandomGenerator generator = new RandomGenerator();
	
	private PaymentPeriod period = new  PaymentPeriod(2018, Quarter.YEAR);
	private User user = generator.generate(User.class);
	
	@Test
	public void calculateForCombinedPayments() {
		FixedPayment fixed1 = generator.generate(FixedPayment.class, BigDecimal.valueOf(100));
		FixedPayment fixed2 = generator.generate(FixedPayment.class, BigDecimal.valueOf(1000));
		when(fixedPaymentService.findFixedPaymentsByYear(eq(period.getYear())))
				.thenReturn(Arrays.asList(fixed1, fixed2));
		when(onePercentCalculator.calculateOnePercentAmount(
				same(user), 
				eq(new PaymentPeriod(2017, Quarter.YEAR)), 
				eq(TaxCalculationSettings.defaults()))
		).thenReturn(BigDecimal.valueOf(550));
		
		List<TaxDeduction> result = strategy.calculateDeductions(user, period);
		
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(period, result.get(0).getPeriod());
		assertTrue("Expected 1650, but was " + result.get(0).getDeduction().toString(), 
				BigDecimal.valueOf(1650).compareTo(result.get(0).getDeduction()) == 0);
	}
	
	@Test
	public void calculateForFixedPaymentsOnly() {
		FixedPayment fixed1 = generator.generate(FixedPayment.class, BigDecimal.valueOf(100));
		FixedPayment fixed2 = generator.generate(FixedPayment.class, BigDecimal.valueOf(1000));
		when(fixedPaymentService.findFixedPaymentsByYear(eq(period.getYear())))
				.thenReturn(Arrays.asList(fixed1, fixed2));
		when(onePercentCalculator.calculateOnePercentAmount(
				same(user),
				eq(new PaymentPeriod(2017, Quarter.YEAR)),
				eq(TaxCalculationSettings.defaults()))
		).thenReturn(BigDecimal.ZERO);

		List<TaxDeduction> result = strategy.calculateDeductions(user, period);

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(period, result.get(0).getPeriod());
		assertTrue("Expected 1100, but was " + result.get(0).getDeduction().toString(),
				BigDecimal.valueOf(1100).compareTo(result.get(0).getDeduction()) == 0);
	}
	
	@Test
	public void calculateForOnePercentOnly() {
		when(fixedPaymentService.findFixedPaymentsByYear(eq(period.getYear())))
				.thenReturn(Collections.emptyList());
		when(onePercentCalculator.calculateOnePercentAmount(
				same(user),
				eq(new PaymentPeriod(2017, Quarter.YEAR)),
				eq(TaxCalculationSettings.defaults()))
		).thenReturn(BigDecimal.valueOf(550));

		List<TaxDeduction> result = strategy.calculateDeductions(user, period);

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(period, result.get(0).getPeriod());
		assertTrue("Expected 550, but was " + result.get(0).getDeduction().toString(),
				BigDecimal.valueOf(550).compareTo(result.get(0).getDeduction()) == 0);
	}
	
	@Test
	public void calculateForEmptyPayments() {
		when(fixedPaymentService.findFixedPaymentsByYear(eq(period.getYear())))
				.thenReturn(Collections.emptyList());
		when(onePercentCalculator.calculateOnePercentAmount(
				same(user),
				eq(new PaymentPeriod(2017, Quarter.YEAR)),
				eq(TaxCalculationSettings.defaults()))
		).thenReturn(BigDecimal.ZERO);

		List<TaxDeduction> result = strategy.calculateDeductions(user, period);

		assertNotNull(result);
		assertEquals(0, result.size());
	}
	
	@Test
	public void calculateForQuarterPeriod() {
		FixedPayment fixed1 = generator.generate(FixedPayment.class, BigDecimal.valueOf(100));
		FixedPayment fixed2 = generator.generate(FixedPayment.class, BigDecimal.valueOf(1000));
		when(fixedPaymentService.findFixedPaymentsByYear(eq(period.getYear())))
				.thenReturn(Arrays.asList(fixed1, fixed2));
		when(onePercentCalculator.calculateOnePercentAmount(
				same(user),
				eq(new PaymentPeriod(2017, Quarter.YEAR)),
				eq(TaxCalculationSettings.defaults()))
		).thenReturn(BigDecimal.ZERO);

		List<TaxDeduction> result = strategy.calculateDeductions(user, period.asQuarter(Quarter.I));

		assertNotNull(result);
		assertEquals(0, result.size());
	}
	
}
