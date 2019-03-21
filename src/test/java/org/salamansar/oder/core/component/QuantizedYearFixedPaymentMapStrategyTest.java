package org.salamansar.oder.core.component;

import java.math.BigDecimal;
import java.util.List;
import static org.envbuild.check.CheckCommonUtils.*;
import org.envbuild.generator.RandomGenerator;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.salamansar.oder.core.domain.FixedPayment;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
import org.salamansar.oder.core.domain.Tax;
import org.salamansar.oder.core.domain.TaxCategory;

/**
 *
 * @author Salamansar
 */
public class QuantizedYearFixedPaymentMapStrategyTest {
	
	private QuantizedYearFixedPaymentMapStrategy strategy = new QuantizedYearFixedPaymentMapStrategy();
	private FixedPaymentAmountCalculator amountCalculator = mock(FixedPaymentAmountCalculator.class);
	private RandomGenerator generator = new RandomGenerator();
	
	@Test
	public void mapFixedPayment() {
		FixedPayment payment = generator.generate(FixedPayment.class, TaxCategory.HEALTH_INSURANCE, BigDecimal.valueOf(101));
		when(amountCalculator.calculate(same(payment))).thenReturn(BigDecimal.valueOf(80.03));
		strategy.initialize(new PaymentPeriod(2018, Quarter.YEAR), amountCalculator);
		
		List<Tax> taxes = strategy.map(payment);
		
		checkList(taxes, 4);
		assertTrue(taxes.stream().allMatch(t -> t.getCatgory() != null && t.getCatgory() == payment.getCategory() 
				&& t.getPeriod() != null && t.getPeriod().getYear().equals(payment.getYear()) 
				&& t.getPayment() != null && t.getPayment().compareTo(BigDecimal.valueOf(80.03)) == 0));
		assertTrue(taxes.stream().anyMatch(t -> t.getPeriod().getQuarter() == Quarter.I));
		assertTrue(taxes.stream().anyMatch(t -> t.getPeriod().getQuarter() == Quarter.II));
		assertTrue(taxes.stream().anyMatch(t -> t.getPeriod().getQuarter() == Quarter.III));
		assertTrue(taxes.stream().anyMatch(t -> t.getPeriod().getQuarter() == Quarter.IV));
	}
	
}
