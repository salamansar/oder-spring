package org.salamansar.oder.core.component;

import java.math.BigDecimal;
import org.junit.Test;
import static org.junit.Assert.*;
import org.salamansar.oder.core.domain.FixedPayment;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
import org.salamansar.oder.core.domain.Tax;
import org.salamansar.oder.core.domain.TaxCategory;

/**
 *
 * @author Salamansar
 */
public class SingleMonthStrategyTest {
    
    @Test
    public void testMapping() {
        SingleMonthStrategy strategy = new SingleMonthStrategy();
        strategy.init(new PaymentPeriod(2018, Quarter.III));
        FixedPayment payment = new FixedPayment();
        payment.setCategory(TaxCategory.HEALTH_INSURANCE);
        payment.setValue(BigDecimal.valueOf(1000.05));
        payment.setYear(2017);
        
        Tax tax = strategy.map(payment);
        
        assertNotNull(tax);
        assertEquals(payment.getCategory(), tax.getCatgory());
        assertTrue(BigDecimal.valueOf(250.0125).compareTo(tax.getPayment()) == 0);
        assertNotNull(tax.getPeriod());
        assertEquals(Quarter.III, tax.getPeriod().getQuarter());
        assertEquals(2018, tax.getPeriod().getYear().intValue());
        
    }
    
}
