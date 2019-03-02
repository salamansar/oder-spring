package org.salamansar.oder.core.utils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import org.envbuild.generator.RandomGenerator;
import org.junit.Test;
import static org.junit.Assert.*;
import org.salamansar.oder.core.domain.FixedPayment;
import org.salamansar.oder.core.domain.Income;
import org.salamansar.oder.core.domain.Tax;

/**
 *
 * @author Salamansar
 */
public class PaymentsUtilsTest {
	private RandomGenerator generator = new RandomGenerator();
	private PaymentsUtils utils = new PaymentsUtils();
	
	@Test
	public void summarizeIncomes() {
		Income income1 = generator.generate(Income.class);
		Income income2 = generator.generate(Income.class);
		
		BigDecimal result = utils.incomesSum(Arrays.asList(income1, income2));
		
		assertNotNull(result);
		assertTrue(income1.getAmount().add(income2.getAmount()).compareTo(result) == 0);
	}
	
	@Test
	public void summarizeEmptyIncomes() {
		BigDecimal result = utils.incomesSum(Collections.emptyList());
		
		assertNotNull(result);
		assertTrue(BigDecimal.ZERO.compareTo(result) == 0);
	}
	
	@Test
	public void summarizeTaxes() {
		Tax tax1 = generator.generate(Tax.class);
		Tax tax2 = generator.generate(Tax.class);
		
		BigDecimal result = utils.taxesSum(Arrays.asList(tax1, tax2));
		
		assertNotNull(result);
		assertTrue(tax1.getPayment().add(tax2.getPayment()).compareTo(result) == 0);
	}
	
	@Test
	public void summarizeEmptyTaxes() {
		BigDecimal result = utils.taxesSum(Collections.emptyList());
		
		assertNotNull(result);
		assertTrue(BigDecimal.ZERO.compareTo(result) == 0);
	}
	
	@Test
	public void summarizeFixedPayments() {
		FixedPayment fixed1 = generator.generate(FixedPayment.class);
		FixedPayment fixed2 = generator.generate(FixedPayment.class);
		
		BigDecimal result = utils.fixedPaymentsSum(Arrays.asList(fixed1, fixed2));
		
		assertNotNull(result);
		assertTrue(fixed1.getValue().add(fixed2.getValue()).compareTo(result) == 0);
	}
	
	@Test
	public void summarizeEmptyFixedPayments() {
		BigDecimal result = utils.fixedPaymentsSum(Collections.emptyList());
		
		assertNotNull(result);
		assertTrue(BigDecimal.ZERO.compareTo(result) == 0);
	}
	
	@Test
	public void summarizeTest() {
		BigDecimal val1 = BigDecimal.valueOf(10);
		BigDecimal val2 = BigDecimal.valueOf(100);
		
		BigDecimal result = utils.summarize(val1, val2);
		
		assertNotNull(result);
		assertTrue(val1.add(val2).compareTo(result) == 0);
	}
	
	@Test
	public void summarizeWithFuncTest() {
		BigDecimal val = BigDecimal.valueOf(10);
		
		BigDecimal result = utils.summarize(Arrays.asList(new Object(), new Object()), t -> val);
		
		assertNotNull(result);
		assertTrue(BigDecimal.valueOf(20).compareTo(result) == 0);
	}
	
	
}
