package org.salamansar.oder.core.service;

import static java.util.Arrays.asList;
import java.util.List;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.mockito.Mockito.*;
import org.salamansar.oder.core.component.DeductCalculatingStrategy;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
import org.salamansar.oder.core.domain.TaxCalculationSettings;
import org.salamansar.oder.core.domain.User;
import org.springframework.test.util.ReflectionTestUtils;

/**
 *
 * @author Salamansar
 */
@RunWith(Parameterized.class)
public class DeductsCalculatorImplTest {
	private static DeductCalculatingStrategy singleMonthStrategy = mock(DeductCalculatingStrategy.class);
	private static DeductCalculatingStrategy allYearStrategy = mock(DeductCalculatingStrategy.class);
	private static DeductCalculatingStrategy quantizedStrategy = mock(DeductCalculatingStrategy.class);
	private static DeductsCalculatorImpl calculator = new DeductsCalculatorImpl();
	
	@Parameterized.Parameter
	public PaymentPeriod period;
	@Parameterized.Parameter(1)
	public TaxCalculationSettings settings;
	@Parameterized.Parameter(2)
	public DeductCalculatingStrategy targetStrategy;
	private User user = new User();

	@Before
	public void setUp() {
		reset(allYearStrategy, quantizedStrategy, singleMonthStrategy);
		ReflectionTestUtils.setField(calculator, "singleMonthStrategy", singleMonthStrategy);
		ReflectionTestUtils.setField(calculator, "allYearStrategy", allYearStrategy);
		ReflectionTestUtils.setField(calculator, "quantizedStrategy", quantizedStrategy);
	}
	
	@Test
	public void testSelectStrategy() {
		calculator.calculateDeducts(user, period, settings);
		
		verify(targetStrategy).calculateDeductions(same(user), same(period));
	}
	
	@Parameterized.Parameters
	public static List<Object[]> parameters() {
		return asList(new Object[][]{
			{new PaymentPeriod(2018, Quarter.I), TaxCalculationSettings.defaults(), singleMonthStrategy},
			{new PaymentPeriod(2018, Quarter.I), TaxCalculationSettings.defaults().splitByQuants(true), singleMonthStrategy},
			{new PaymentPeriod(2018, Quarter.II), TaxCalculationSettings.defaults(), singleMonthStrategy},
			{new PaymentPeriod(2018, Quarter.II), TaxCalculationSettings.defaults().splitByQuants(true), singleMonthStrategy},
			{new PaymentPeriod(2018, Quarter.III), TaxCalculationSettings.defaults(), singleMonthStrategy},
			{new PaymentPeriod(2018, Quarter.III), TaxCalculationSettings.defaults().splitByQuants(true), singleMonthStrategy},
			{new PaymentPeriod(2018, Quarter.IV), TaxCalculationSettings.defaults(), singleMonthStrategy},
			{new PaymentPeriod(2018, Quarter.IV), TaxCalculationSettings.defaults().splitByQuants(true), singleMonthStrategy},
			{new PaymentPeriod(2018, Quarter.YEAR), TaxCalculationSettings.defaults(), allYearStrategy},
			{new PaymentPeriod(2018, Quarter.YEAR), TaxCalculationSettings.defaults().splitByQuants(true), quantizedStrategy},
		});
	}
}
