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
public class IncomeMapStrategyFactoryImplTest {
	@Mock
	private IncomeMapStrategy summarizedStrategy;
	@Mock
	private IncomeMapStrategy quantizedStrategy;
	private IncomeMapStrategyFactoryImpl factory;
	
	@Before
	public void setUp() {
		factory = new IncomeMapStrategyFactoryImpl();
		ReflectionTestUtils.setField(factory, "summarizedStrategy", summarizedStrategy);
		ReflectionTestUtils.setField(factory, "quantizedStrategy", quantizedStrategy);
	}

	@Test
	public void testGetSummarizedStrategy() {
		IncomeMapStrategy result = factory.getStrategy(new PaymentPeriod(2018, Quarter.YEAR), new TaxCalculationSettings());
		
		assertSame(summarizedStrategy, result);
	}
	
	@Test
	public void testGetQuantizedStrategy() {
		final TaxCalculationSettings settings = new TaxCalculationSettings();
		settings.setByQuants(true);
		
		IncomeMapStrategy result = factory.getStrategy(new PaymentPeriod(2018, Quarter.YEAR), settings);
		
		assertSame(quantizedStrategy, result);
		
		result = factory.getStrategy(new PaymentPeriod(2018, Quarter.I), settings);

		assertSame(quantizedStrategy, result);
	}
	
}
