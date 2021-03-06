package org.salamansar.oder.core.component;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
import org.salamansar.oder.core.domain.TaxCalculationSettings;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.test.util.ReflectionTestUtils;

/**
 *
 * @author Salamansar
 */
public class FixedPaymentMapStrategyFactoryImplTest {
    private FixedPaymentMapStrategy allYearsStrategy;
    private FixedPaymentMapStrategy singleMonthStrategy;
    private FixedPaymentMapStrategy quantizedYearStrategy;
    private FixedPaymentMapStrategyFactoryImpl factory;
    
    @Before
    public void setUp() {
        allYearsStrategy = mock(FixedPaymentMapStrategy.class);
        singleMonthStrategy = mock(FixedPaymentMapStrategy.class, withSettings()
                .extraInterfaces(PeriodInitialized.class));
		quantizedYearStrategy = mock(FixedPaymentMapStrategy.class);
        ObjectFactory<FixedPaymentMapStrategy> allYearsFactory = mock(ObjectFactory.class);
        when(allYearsFactory.getObject()).thenReturn(allYearsStrategy);
        ObjectFactory<FixedPaymentMapStrategy> singleMonthFactory = mock(ObjectFactory.class);
        when(singleMonthFactory.getObject()).thenReturn(singleMonthStrategy);
        ObjectFactory<FixedPaymentMapStrategy> quantizedYearFactory = mock(ObjectFactory.class);
        when(quantizedYearFactory.getObject()).thenReturn(quantizedYearStrategy);
        factory = new FixedPaymentMapStrategyFactoryImpl();
        ReflectionTestUtils.setField(factory, "allYearsStrategy", allYearsFactory);
        ReflectionTestUtils.setField(factory, "singleMonthStrategy", singleMonthFactory);
        ReflectionTestUtils.setField(factory, "quantizedYearStrategy", quantizedYearFactory);
    }

    @Test
    public void selectAllYears() {
        FixedPaymentMapStrategy strategy = factory.getStrategy(new PaymentPeriod(2018, Quarter.YEAR), 
				new TaxCalculationSettings().withRoundUp(true));
        
        assertSame(allYearsStrategy, strategy);
		verify(allYearsStrategy).initialize(
				eq(new PaymentPeriod(2018, Quarter.YEAR)),
				isA(PlainFixedPaymentAmountCalculator.class));
    }

	@Test
    public void selectSingleMonth() {
        FixedPaymentMapStrategy strategy = factory.getStrategy(new PaymentPeriod(2018, Quarter.IV), TaxCalculationSettings.defaults());
        
        assertSame(singleMonthStrategy, strategy);
        verify(singleMonthStrategy).initialize(
				eq(new PaymentPeriod(2018, Quarter.IV)), 
				isA(QuarterFixedPaymentAmountCalculator.class));
    }
	
	@Test
	public void selectQuantizedPaymentsForYear() {
		TaxCalculationSettings settings = new TaxCalculationSettings()
				.splitByQuants(true)
				.withRoundUp(true);
		
		FixedPaymentMapStrategy strategy = factory.getStrategy(new PaymentPeriod(2018, Quarter.YEAR), settings);

		assertSame(quantizedYearStrategy, strategy);
		verify(quantizedYearStrategy).initialize(
				eq(new PaymentPeriod(2018, Quarter.YEAR)),
				isA(QuarterFixedPaymentAmountCalculator.class));
	}
}
