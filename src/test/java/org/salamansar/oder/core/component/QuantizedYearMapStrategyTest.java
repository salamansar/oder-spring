package org.salamansar.oder.core.component;

import java.math.BigDecimal;
import java.util.List;
import static org.envbuild.check.CheckCommonUtils.*;
import org.envbuild.generator.RandomGenerator;
import org.junit.Test;
import static org.junit.Assert.*;
import org.salamansar.oder.core.domain.FixedPayment;
import org.salamansar.oder.core.domain.Quarter;
import org.salamansar.oder.core.domain.Tax;
import org.salamansar.oder.core.domain.TaxCategory;

/**
 *
 * @author Salamansar
 */
public class QuantizedYearMapStrategyTest {
	
	private QuantizedYearFixedPaymentMapStrategy strategy = new QuantizedYearFixedPaymentMapStrategy();
	private RandomGenerator generator = new RandomGenerator();
	
	@Test
	public void mapFixedPayment() {
		FixedPayment payment = generator.generate(FixedPayment.class, TaxCategory.HEALTH_INSURANCE);
		payment.setValue(BigDecimal.valueOf(101));
		
		List<Tax> taxes = strategy.map(payment);
		
		checkList(taxes, 4);
		assertTrue(taxes.stream().allMatch(t -> t.getCatgory() != null && t.getCatgory() == payment.getCategory() 
				&& t.getPeriod() != null && t.getPeriod().getYear().equals(payment.getYear()) 
				&& t.getPayment() != null && t.getPayment().compareTo(BigDecimal.valueOf(25.25)) == 0));
		assertTrue(taxes.stream().anyMatch(t -> t.getPeriod().getQuarter() == Quarter.I));
		assertTrue(taxes.stream().anyMatch(t -> t.getPeriod().getQuarter() == Quarter.II));
		assertTrue(taxes.stream().anyMatch(t -> t.getPeriod().getQuarter() == Quarter.III));
		assertTrue(taxes.stream().anyMatch(t -> t.getPeriod().getQuarter() == Quarter.IV));
	}
	
}
