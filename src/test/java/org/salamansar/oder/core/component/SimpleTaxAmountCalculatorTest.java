package org.salamansar.oder.core.component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;
import static org.junit.Assert.*;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
import org.salamansar.oder.core.domain.QuarterIncome;

/**
 *
 * @author Salamansar
 */
public class SimpleTaxAmountCalculatorTest {
	private SimpleTaxAmountCalculator amountCalculator = new SimpleTaxAmountCalculator();
	
	@Test
	public void calculateIncomes() {
		QuarterIncome income1 = new QuarterIncome(new PaymentPeriod(2018, Quarter.I), BigDecimal.valueOf(100.50));
		QuarterIncome income2 = new QuarterIncome(new PaymentPeriod(2018, Quarter.II), BigDecimal.valueOf(50.50));

		BigDecimal result = amountCalculator.calculateTax(Arrays.asList(income1, income2));

		assertNotNull(result);
		assertTrue(BigDecimal.valueOf(9.06).compareTo(result) == 0);
	}
	
	@Test
	public void calculateEmptyIncomes() {
		BigDecimal result = amountCalculator.calculateTax(Collections.emptyList());

		assertNotNull(result);
		assertTrue(BigDecimal.ZERO.compareTo(result) == 0);
	}
	
	@Test
	public void calculateSingleIncome() {
		QuarterIncome income = new QuarterIncome(new PaymentPeriod(2018, Quarter.I), BigDecimal.valueOf(100.50));

		BigDecimal result = amountCalculator.calculateTax(income);

		assertNotNull(result);
		assertTrue(BigDecimal.valueOf(6.03).compareTo(result) == 0);
	}
	
	@Test
	public void calculateNullIncome() {
		QuarterIncome income = new QuarterIncome();
		
		BigDecimal result = amountCalculator.calculateTax(income);

		assertNull(result);
	}
	
}
