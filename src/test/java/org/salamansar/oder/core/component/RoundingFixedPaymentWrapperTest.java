package org.salamansar.oder.core.component;

import java.math.BigDecimal;
import org.envbuild.generator.RandomGenerator;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.salamansar.oder.core.domain.FixedPayment;

/**
 *
 * @author Salamansar
 */
public class RoundingFixedPaymentWrapperTest {
	
	private RandomGenerator generator = new RandomGenerator();
	private FixedPaymentAmountCalculator mainCalculator = mock(FixedPaymentAmountCalculator.class);
	private RoundingFixedPaymentWrapper calculator = new RoundingFixedPaymentWrapper(mainCalculator);
	
	@Test
	public void getValue() {
		FixedPayment payment = generator.generate(FixedPayment.class, true);
		when(mainCalculator.calculate(same(payment))).thenReturn(BigDecimal.valueOf(100.25));
		
		BigDecimal result = calculator.calculate(payment);
		
		assertNotNull(result);
		assertTrue(BigDecimal.valueOf(101).compareTo(result) == 0);
	}
	
	@Test
	public void getNullValue() {
		reset(mainCalculator);
		FixedPayment payment = generator.generate(FixedPayment.class, true);

		BigDecimal result = calculator.calculate(payment);

		assertNull(result);
	}
	
}
