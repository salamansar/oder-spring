package org.salamansar.oder.core.domain;

import java.math.BigDecimal;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Salamansar
 */
public class DeductibleTaxTest {
	
	@Test
	public void calculateDeductedPayment() {
		DeductibleTax tax = new DeductibleTax();
		tax.setPayment(BigDecimal.valueOf(1000));
		tax.setDeduction(BigDecimal.valueOf(100));
		
		assertTrue(BigDecimal.valueOf(900).compareTo(tax.getDeductedPayment()) == 0);
	}
	
	@Test
	public void calculateDeductedWithEmptyDeduction() {
		DeductibleTax tax = new DeductibleTax();
		tax.setPayment(BigDecimal.valueOf(1000));

		assertTrue(BigDecimal.valueOf(1000).compareTo(tax.getDeductedPayment()) == 0);
	}
	
	@Test
	public void calculateDeductedWithEmptyTax() {
		DeductibleTax tax = new DeductibleTax();
		tax.setDeduction(BigDecimal.valueOf(100));

		assertTrue(BigDecimal.ZERO.compareTo(tax.getDeductedPayment()) == 0);
	}
	
	
	@Test
	public void calculateDeductedWithLessTaxThenDeduction() {
		DeductibleTax tax = new DeductibleTax();
		tax.setPayment(BigDecimal.valueOf(100));
		tax.setDeduction(BigDecimal.valueOf(101));

		assertTrue(BigDecimal.ZERO.compareTo(tax.getDeductedPayment()) == 0);
	}
	
	@Test
	public void calculateDeductedWithBothEmpty() {
		DeductibleTax tax = new DeductibleTax();

		assertTrue(BigDecimal.ZERO.compareTo(tax.getDeductedPayment()) == 0);
	}
}
