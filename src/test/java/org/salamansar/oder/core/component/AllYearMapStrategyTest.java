package org.salamansar.oder.core.component;

import java.math.BigDecimal;
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
public class AllYearMapStrategyTest {

    private AllYearMapStrategy strategy = new AllYearMapStrategy();

    @Test
    public void testMapping() {
        FixedPayment payment = new FixedPayment();
        payment.setCategory(TaxCategory.HEALTH_INSURANCE);
        payment.setValue(BigDecimal.valueOf(1000L));
        payment.setYear(2018);
        
        Tax tax = strategy.map(payment);
        
        assertNotNull(tax);
        assertEquals(payment.getCategory(), tax.getCatgory());
        assertTrue(payment.getValue().compareTo(tax.getPayment()) == 0);
        assertNotNull(tax.getPeriod());
        assertEquals(Quarter.YEAR, tax.getPeriod().getQuarter());
        assertEquals(payment.getYear(), tax.getPeriod().getYear());
    }
    
}
