package org.salamansar.oder.core.component;

import java.math.BigDecimal;
import java.util.List;
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
public class SingleMonthFixedPaymentStrategyTest {
	private FixedPaymentAmountCalculator amountCalculator = mock(FixedPaymentAmountCalculator.class);
    
    @Test
    public void testMapping() {
        SingleMonthFixedPaymentStrategy strategy = new SingleMonthFixedPaymentStrategy();
        strategy.initialize(new PaymentPeriod(2018, Quarter.III), amountCalculator);
        FixedPayment payment = new FixedPayment();
        payment.setCategory(TaxCategory.HEALTH_INSURANCE);
        payment.setValue(BigDecimal.valueOf(1000.05));
        payment.setYear(2017);
		when(amountCalculator.calculate(same(payment))).thenReturn(BigDecimal.valueOf(100.02));
        
        List<Tax> taxes = strategy.map(payment);
        
        assertNotNull(taxes);
		assertEquals(1, taxes.size());
		Tax tax = taxes.get(0);
        assertEquals(payment.getCategory(), tax.getCatgory());
        assertTrue(BigDecimal.valueOf(100.02).compareTo(tax.getPayment()) == 0);
        assertNotNull(tax.getPeriod());
        assertEquals(Quarter.III, tax.getPeriod().getQuarter());
        assertEquals(2018, tax.getPeriod().getYear().intValue());
        
    }
    
}
