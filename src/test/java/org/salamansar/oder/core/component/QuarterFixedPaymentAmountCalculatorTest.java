package org.salamansar.oder.core.component;

import java.math.BigDecimal;
import org.envbuild.generator.RandomGenerator;
import org.junit.Test;
import static org.junit.Assert.*;
import org.salamansar.oder.core.domain.FixedPayment;

/**
 *
 * @author Salamansar
 */
public class QuarterFixedPaymentAmountCalculatorTest {
	
	private QuarterFixedPaymentAmountCalculator calculator = new QuarterFixedPaymentAmountCalculator();
	private RandomGenerator generator = new RandomGenerator();
	
	@Test
	public void getValue() {
		FixedPayment payment = generator.generate(FixedPayment.class, true, BigDecimal.valueOf(125.25));

		BigDecimal result = calculator.calculate(payment);

		assertNotNull(result);
		assertTrue(BigDecimal.valueOf(31.3125).compareTo(result) == 0);
	}

	@Test
	public void getNullValue() {
		FixedPayment payment = new FixedPayment();

		BigDecimal result = calculator.calculate(payment);

		assertNull(result);

		result = calculator.calculate(null);

		assertNull(result);
	}
	
}
