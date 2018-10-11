package org.salamansar.oder.core.component;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.test.util.ReflectionTestUtils;

/**
 *
 * @author Salamansar
 */
public class FixedPaymentMapStrategyFactoryImplTest {
    private FixedPaymentMapStrategy allYearsStrategy;
    private FixedPaymentMapStrategy singleMonthStrategy;
    private FixedPaymentMapStrategyFactoryImpl factory;
    
    @Before
    public void setUp() {
        allYearsStrategy = mock(FixedPaymentMapStrategy.class);
        singleMonthStrategy = mock(FixedPaymentMapStrategy.class, withSettings()
                .extraInterfaces(PeriodInitialized.class));
        ObjectFactory<FixedPaymentMapStrategy> allYearsFactory = mock(ObjectFactory.class);
        when(allYearsFactory.getObject()).thenReturn(allYearsStrategy);
        ObjectFactory<FixedPaymentMapStrategy> singleMonthFactory = mock(ObjectFactory.class);
        when(singleMonthFactory.getObject()).thenReturn(singleMonthStrategy);
        factory = new FixedPaymentMapStrategyFactoryImpl();
        ReflectionTestUtils.setField(factory, "allYearsStrategy", allYearsFactory);
        ReflectionTestUtils.setField(factory, "singleMonthStrategy", singleMonthFactory);
    }

    @Test
    public void selectAllYears() {
        FixedPaymentMapStrategy strategy = factory.getStartegy(new PaymentPeriod(2018, Quarter.YEAR));
        
        assertSame(allYearsStrategy, strategy);
    }
    
    @Test
    public void selectSingleMonth() {
        FixedPaymentMapStrategy strategy = factory.getStartegy(new PaymentPeriod(2018, Quarter.IV));
        
        assertSame(singleMonthStrategy, strategy);
        PeriodInitialized initializedStrategy = (PeriodInitialized) singleMonthStrategy;
        verify(initializedStrategy).init(eq(new PaymentPeriod(2018, Quarter.IV)));
    }
    
}
