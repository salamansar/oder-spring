package org.salamansar.oder.core.component;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import org.springframework.test.util.ReflectionTestUtils;

/**
 *
 * @author Salamansar
 */
public class QuarterIncomeMapStrategyFactoryImplTest {
	private QuarterIncomeMapStrategy quantizedStrategy;
	private QuarterIncomeMapStrategy summarizedStrategy;
	private QuarterIncomeMapStrategyFactoryImpl factory;
	
	@Before
	public void setUp() {
		quantizedStrategy = mock(QuarterIncomeMapStrategy.class);
		summarizedStrategy = mock(QuarterIncomeMapStrategy.class);
		factory = new QuarterIncomeMapStrategyFactoryImpl();
		ReflectionTestUtils.setField(factory, "quantizedStrategy", quantizedStrategy);
		ReflectionTestUtils.setField(factory, "summarizedStrategy", summarizedStrategy);
	}

	@Test
	public void getStrategy() {
		QuarterIncomeMapStrategy strategy = factory.getStrategy(true);
		
		assertSame(quantizedStrategy, strategy);
		
		strategy = factory.getStrategy(false);
		
		assertSame(summarizedStrategy, strategy);
	}
	
}
