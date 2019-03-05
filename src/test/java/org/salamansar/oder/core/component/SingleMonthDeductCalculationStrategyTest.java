package org.salamansar.oder.core.component;

import java.math.BigDecimal;
import static java.util.Arrays.asList;
import java.util.Collections;
import java.util.List;
import org.envbuild.generator.RandomGenerator;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.mockito.Mockito.*;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
import static org.salamansar.oder.core.domain.Quarter.*;
import org.salamansar.oder.core.domain.Tax;
import org.salamansar.oder.core.domain.TaxCalculationSettings;
import org.salamansar.oder.core.domain.TaxDeduction;
import org.salamansar.oder.core.domain.User;
import org.salamansar.oder.core.service.FixedPaymentTaxCalculator;
import org.salamansar.oder.core.service.OnePercentTaxCalculator;
import org.springframework.test.util.ReflectionTestUtils;

/**
 *
 * @author Salamansar
 */
@RunWith(Parameterized.class)
public class SingleMonthDeductCalculationStrategyTest {
	@Parameterized.Parameter
	public PaymentPeriod period;
	@Parameterized.Parameter(1)
	public List<Tax> taxes;
	@Parameterized.Parameter(2)
	public BigDecimal onePercent;
	@Parameterized.Parameter(3)
	public List<TaxDeduction> expected;
	
	private static FixedPaymentTaxCalculator fixedPaymentCalculator = mock(FixedPaymentTaxCalculator.class);
	private static OnePercentTaxCalculator onePercentCalculator = mock(OnePercentTaxCalculator.class);
	private static RandomGenerator generator = new RandomGenerator();
	private static User user = new User();
	private static int year = 2018;
	private SingleMonthDeductCalculationStrategy strategy = new SingleMonthDeductCalculationStrategy();
	
	@Before
	public void setUp() {
		when(fixedPaymentCalculator.calculateFixedPayments(eq(period),
				eq(TaxCalculationSettings.defaults())))
				.thenReturn(taxes);
		when(onePercentCalculator.calculateOnePercentAmount(same(user),
				eq(new PaymentPeriod(year - 1, Quarter.YEAR)),
				eq(TaxCalculationSettings.defaults())))
				.thenReturn(onePercent);
		ReflectionTestUtils.setField(strategy, "fixedPaymentCalculator", fixedPaymentCalculator);
		ReflectionTestUtils.setField(strategy, "onePercentCalculator", onePercentCalculator);
	}
	

	@Test
	public void runTest() {
		List<TaxDeduction> result = strategy.calculateDeductions(user, period);
		
		assertNotNull(result);
		assertEquals(expected.size(), result.size());
		assertTrue(result.containsAll(expected));
	}
	
	@Parameterized.Parameters
	public static List<Object[]> parameters() {
		return asList(new Object[][] {
			{pp(I), asList(tx(100, I), tx(1000, II)), BigDecimal.valueOf(500), asList(td(1600, I))},
			{pp(I), asList(tx(100, I), tx(1000, II)), BigDecimal.ZERO, asList(td(1100, I))},
			{pp(I), Collections.emptyList(), BigDecimal.valueOf(500), asList(td(500, I))},
			{pp(I), Collections.emptyList(), BigDecimal.ZERO, Collections.emptyList()},
			{pp(II), asList(tx(100, I), tx(1000, II)), BigDecimal.valueOf(500), asList(td(1100, II))},
			{pp(III), asList(tx(100, I), tx(1000, II)), BigDecimal.valueOf(500), asList(td(1100, III))},
			{pp(IV), asList(tx(100, I), tx(1000, II)), BigDecimal.valueOf(500), asList(td(1100, IV))},
			{pp(YEAR), asList(tx(100, I), tx(1000, II)), BigDecimal.valueOf(500), Collections.emptyList()},
		});
	}
	
	private static Tax tx(int val, Quarter quarter) {
		return generator.generate(Tax.class, BigDecimal.valueOf(val), pp(quarter));
	}
	
	private static PaymentPeriod pp(Quarter quarter) {
		return new PaymentPeriod(year, quarter);
	}
	
	private static TaxDeduction td(Integer val, Quarter quarter) {
		return new TaxDeduction(pp(quarter), BigDecimal.valueOf(val));
	}
	
}
