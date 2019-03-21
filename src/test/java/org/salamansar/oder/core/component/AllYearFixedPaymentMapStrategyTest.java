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
public class AllYearFixedPaymentMapStrategyTest {

	private FixedPaymentAmountCalculator amountCalculator = mock(FixedPaymentAmountCalculator.class);
    private AllYearFixedPaymentMapStrategy strategy = new AllYearFixedPaymentMapStrategy();

    @Test
    public void testMapping() {
        FixedPayment payment = new FixedPayment();
        payment.setCategory(TaxCategory.HEALTH_INSURANCE);
        payment.setValue(BigDecimal.valueOf(1000L));
        payment.setYear(2018);
		when(amountCalculator.calculate(same(payment))).thenReturn(BigDecimal.valueOf(80.03));
		strategy.initialize(new PaymentPeriod(2018, Quarter.YEAR), amountCalculator);
        
        List<Tax> taxes = strategy.map(payment);
        
        assertNotNull(taxes);
		assertEquals(1, taxes.size());
		Tax tax = taxes.get(0);
        assertEquals(payment.getCategory(), tax.getCatgory());
        assertTrue(BigDecimal.valueOf(80.03).compareTo(tax.getPayment()) == 0);
        assertNotNull(tax.getPeriod());
        assertEquals(Quarter.YEAR, tax.getPeriod().getQuarter());
        assertEquals(payment.getYear(), tax.getPeriod().getYear());
    }
    
}
