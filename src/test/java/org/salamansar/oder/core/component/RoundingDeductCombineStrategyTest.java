package org.salamansar.oder.core.component;

import java.math.BigDecimal;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Salamansar
 */
public class RoundingDeductCombineStrategyTest {
	
	private RoundingDeductCombineStrategy strategy = new RoundingDeductCombineStrategy();

	@Test
	public void calculateDeductedPayment() {
		BigDecimal result = strategy.applyDeduct(BigDecimal.valueOf(1000.35), BigDecimal.valueOf(100.5));

		assertTrue(BigDecimal.valueOf(901).compareTo(result) == 0);
	}

	@Test
	public void calculateDeductedWithEmptyDeduction() {
		BigDecimal result = strategy.applyDeduct(BigDecimal.valueOf(1000.35), null);

		assertTrue(BigDecimal.valueOf(1001).compareTo(result) == 0);
	}

	@Test
	public void calculateDeductedWithEmptyTax() {
		BigDecimal result = strategy.applyDeduct(null, BigDecimal.valueOf(100.5));

		assertNull(result);
	}

	@Test
	public void calculateDeductedWithLessTaxThenDeduction() {
		BigDecimal result = strategy.applyDeduct(BigDecimal.valueOf(98.35), BigDecimal.valueOf(100.99));

		assertTrue(BigDecimal.ZERO.compareTo(result) == 0);
	}

	@Test
	public void calculateDeductedWithBothEmpty() {
		BigDecimal result = strategy.applyDeduct(null, null);

		assertNull(result);
	}
	
}
