package org.salamansar.oder.core.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.envbuild.generator.RandomGenerator;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.same;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;
import org.salamansar.oder.core.component.FixedPaymentMapStrategy;
import org.salamansar.oder.core.component.FixedPaymentMapStrategyFactory;
import org.salamansar.oder.core.domain.FixedPayment;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
import org.salamansar.oder.core.domain.Tax;
import org.salamansar.oder.core.domain.TaxCalculationSettings;

/**
 *
 * @author Salamansar
 */
@RunWith(MockitoJUnitRunner.class)
public class FixedPaymentTaxCalculatorImplTest {
	@Mock
	private FixedPaymentMapStrategyFactory fixedPaymentMapFactory;
	@Mock
	private FixedPaymentMapStrategy mapStrategy;
	@Mock
	private FixedPaymentService fixedPaymentService;
	@InjectMocks
	private FixedPaymentTaxCalculatorImpl taxCalculator = new FixedPaymentTaxCalculatorImpl();
	private RandomGenerator generator = new RandomGenerator();
	
	@Before
	public void setUp() {
		when(fixedPaymentMapFactory.getStrategy(any(PaymentPeriod.class), any(TaxCalculationSettings.class)))
				.thenReturn(mapStrategy);
		when(fixedPaymentService.findFixedPaymentsByYear(anyInt()))
				.thenReturn(Collections.emptyList());
	}
	
	
	@Test
	public void calculateFixedPayments() {
		FixedPayment payment = generator.generate(FixedPayment.class);
		Tax tax = generator.generate(Tax.class);
		when(fixedPaymentService.findFixedPaymentsByYear(eq(2018)))
				.thenReturn(Arrays.asList(payment));
		when(mapStrategy.map(same(payment))).thenReturn(Arrays.asList(tax));

		List<Tax> result = taxCalculator.calculateFixedPayments(new PaymentPeriod(2018, Quarter.YEAR), TaxCalculationSettings.defaults());

		assertNotNull(result);
		assertEquals(1, result.size());
		assertSame(tax, result.get(0));
	}

	@Test
	public void calculateEmptyListFixedPayments() {
		List<Tax> result = taxCalculator.calculateFixedPayments(new PaymentPeriod(2018, Quarter.YEAR), TaxCalculationSettings.defaults());

		assertNotNull(result);
		assertTrue(result.isEmpty());
	}
	
}
