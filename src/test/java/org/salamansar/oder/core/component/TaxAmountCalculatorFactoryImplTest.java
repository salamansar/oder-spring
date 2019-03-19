package org.salamansar.oder.core.component;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
import org.salamansar.oder.core.domain.TaxCalculationSettings;
import org.springframework.test.util.ReflectionTestUtils;

/**
 *
 * @author Salamansar
 */
@RunWith(MockitoJUnitRunner.class)
public class TaxAmountCalculatorFactoryImplTest {
	@Mock
	private TaxAmountCalculator simpleCalculator;
	@Mock
	private TaxAmountCalculator roundedCalculator;
	private TaxAmountCalculatorFactoryImpl factory;
	
	@Before
	public void setUp() {
		factory = new TaxAmountCalculatorFactoryImpl();
		ReflectionTestUtils.setField(factory, "simpleCalculator", simpleCalculator);
		ReflectionTestUtils.setField(factory, "roundedCalculator", roundedCalculator);
	}

	@Test
	public void testGetSimpleCalculator() {
		TaxAmountCalculator result = factory.getCalculator(new TaxCalculationSettings());
		
		assertSame(simpleCalculator, result);
	}
	
	@Test
	public void testGetRoundingCalculator() {
		final TaxCalculationSettings settings = new TaxCalculationSettings().withRoundUp(true);
		
		TaxAmountCalculator result = factory.getCalculator(settings);
		
		assertSame(roundedCalculator, result);
	}
	
}
