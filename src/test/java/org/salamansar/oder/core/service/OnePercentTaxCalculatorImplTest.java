package org.salamansar.oder.core.service;

import java.math.BigDecimal;
import java.util.List;
import org.envbuild.generator.RandomGenerator;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
import org.salamansar.oder.core.domain.QuarterIncome;
import org.salamansar.oder.core.domain.Tax;
import org.salamansar.oder.core.domain.TaxCalculationSettings;
import org.salamansar.oder.core.domain.TaxCategory;
import org.salamansar.oder.core.domain.User;

/**
 *
 * @author Salamansar
 */
@RunWith(MockitoJUnitRunner.class)
public class OnePercentTaxCalculatorImplTest {
	@Mock
	private IncomeService incomesService;
	@InjectMocks
	private OnePercentTaxCalculatorImpl calculator = new OnePercentTaxCalculatorImpl();
	private RandomGenerator generator = new RandomGenerator();
	private User user;
	private Integer testYear = 2018;

	@Before
	public void setUp() {
		user = generator.generate(User.class);
		when(incomesService.findSingleIncome(same(user), eq(new PaymentPeriod(testYear, Quarter.YEAR))))
				.thenReturn(
						new QuarterIncome(
								new PaymentPeriod(testYear, Quarter.YEAR),
								BigDecimal.valueOf(600000)
						)
				);
		when(incomesService.findSingleIncome(same(user), eq(new PaymentPeriod(testYear - 1, Quarter.YEAR))))
				.thenReturn(
						new QuarterIncome(
								new PaymentPeriod(testYear - 1, Quarter.YEAR),
								BigDecimal.valueOf(300000.25)
						)
				);
	}

	@Test
	public void claculatePercentForYear() {
		PaymentPeriod period = new PaymentPeriod(testYear, Quarter.YEAR);
		TaxCalculationSettings settings = new TaxCalculationSettings();
		
		List<Tax> result = calculator.calculateOnePercentTaxes(user, period, settings);

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(new PaymentPeriod(testYear, Quarter.YEAR), result.get(0).getPeriod());
		assertEquals(TaxCategory.PENSION_PERCENT, result.get(0).getCatgory());
		assertTrue(BigDecimal.valueOf(3000).compareTo(result.get(0).getPayment()) == 0);
		
		BigDecimal amount = calculator.calculateOnePercentAmount(user, period, settings);
		
		assertNotNull(amount);
		assertTrue(BigDecimal.valueOf(3000).compareTo(amount) == 0);
	}
	
	@Test
	public void claculatePercentForYearWhenNotEnoughAmount() {
		when(incomesService.findSingleIncome(same(user), eq(new PaymentPeriod(testYear, Quarter.YEAR))))
				.thenReturn(
						new QuarterIncome(
								new PaymentPeriod(testYear, Quarter.YEAR),
								BigDecimal.valueOf(299999.99)
						)
				);
		PaymentPeriod period = new PaymentPeriod(testYear, Quarter.YEAR);
		TaxCalculationSettings settings = new TaxCalculationSettings();
		
		List<Tax> result = calculator.calculateOnePercentTaxes(user, period, settings);

		assertNotNull(result);
		assertEquals(0, result.size());

		BigDecimal amount = calculator.calculateOnePercentAmount(user, period, settings);

		assertNotNull(amount);
		assertTrue(BigDecimal.ZERO.compareTo(amount) == 0);
	}
	
	@Test
	public void claculatePercentForYearWhenEmptyIncomes() {
		reset(incomesService);
		PaymentPeriod period = new PaymentPeriod(testYear, Quarter.YEAR);
		TaxCalculationSettings settings = new TaxCalculationSettings();
		
		List<Tax> result = calculator.calculateOnePercentTaxes(user, period, settings);

		assertNotNull(result);
		assertEquals(0, result.size());
		
		BigDecimal amount = calculator.calculateOnePercentAmount(user, period, settings);

		assertNotNull(amount);
		assertTrue(BigDecimal.ZERO.compareTo(amount) == 0);
	}
	
	@Test
	public void claculateForQuantizedYear() {
		PaymentPeriod period = new PaymentPeriod(testYear, Quarter.YEAR);
		TaxCalculationSettings settings = new TaxCalculationSettings().splitByQuants(true);
		
		List<Tax> result = calculator.calculateOnePercentTaxes(user, period, settings);

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(new PaymentPeriod(testYear, Quarter.I), result.get(0).getPeriod());
		assertEquals(TaxCategory.PENSION_PERCENT, result.get(0).getCatgory());
		assertTrue(BigDecimal.valueOf(0.0025).compareTo(result.get(0).getPayment()) == 0);
		
		BigDecimal amount = calculator.calculateOnePercentAmount(user, period, settings);

		assertNotNull(amount);
		assertTrue(BigDecimal.ZERO.compareTo(amount) == 0);
	}
	
	@Test
	public void claculateForQuantizedYearWhenNotEnoughAmount() {
		when(incomesService.findSingleIncome(same(user), eq(new PaymentPeriod(testYear - 1, Quarter.YEAR))))
				.thenReturn(
						new QuarterIncome(
								new PaymentPeriod(testYear - 1, Quarter.YEAR),
								BigDecimal.valueOf(300000)
						)
				);
		PaymentPeriod period = new PaymentPeriod(testYear, Quarter.YEAR);
		TaxCalculationSettings settings = new TaxCalculationSettings().splitByQuants(true);
		
		List<Tax> result = calculator.calculateOnePercentTaxes(user, period, settings);

		assertNotNull(result);
		assertEquals(0, result.size());
		
		BigDecimal amount = calculator.calculateOnePercentAmount(user, period, settings);

		assertNotNull(amount);
		assertTrue(BigDecimal.ZERO.compareTo(amount) == 0);
	}
	
	@Test
	public void claculateForQuantizedYearWhenEmptyIncomes() {
		reset(incomesService);
		PaymentPeriod period = new PaymentPeriod(testYear, Quarter.YEAR);
		TaxCalculationSettings settings = new TaxCalculationSettings().splitByQuants(true);
		
		List<Tax> result = calculator.calculateOnePercentTaxes(user, period, settings);

		assertNotNull(result);
		assertEquals(0, result.size());
		
		BigDecimal amount = calculator.calculateOnePercentAmount(user, period, settings);

		assertNotNull(amount);
		assertTrue(BigDecimal.ZERO.compareTo(amount) == 0);
	}
	
	@Test
	public void claculateForFirstQuarter() {
		PaymentPeriod period = new PaymentPeriod(testYear, Quarter.I);
		TaxCalculationSettings settings = new TaxCalculationSettings();
		
		List<Tax> result = calculator.calculateOnePercentTaxes(user, period, settings);

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(new PaymentPeriod(testYear, Quarter.I), result.get(0).getPeriod());
		assertEquals(TaxCategory.PENSION_PERCENT, result.get(0).getCatgory());
		assertTrue(BigDecimal.valueOf(0.0025).compareTo(result.get(0).getPayment()) == 0);
		
		BigDecimal amount = calculator.calculateOnePercentAmount(user, period, settings);

		assertNotNull(amount);
		assertTrue(BigDecimal.valueOf(0.0025).compareTo(amount) == 0);
	}
	
	@Test
	public void claculateForSecondQuarter() {
		PaymentPeriod period = new PaymentPeriod(testYear, Quarter.II);
		TaxCalculationSettings settings = new TaxCalculationSettings();
		
		List<Tax> result = calculator.calculateOnePercentTaxes(user, period, settings);

		assertNotNull(result);
		assertEquals(0, result.size());
		
		BigDecimal amount = calculator.calculateOnePercentAmount(user, period, settings);

		assertNotNull(amount);
		assertTrue(BigDecimal.ZERO.compareTo(amount) == 0);
	}
	
	@Test
	public void claculateForThirdQuarter() {
		PaymentPeriod period = new PaymentPeriod(testYear, Quarter.III);
		TaxCalculationSettings settings = new TaxCalculationSettings();
		settings.setByQuants(true);
		
		List<Tax> result = calculator.calculateOnePercentTaxes(user, period, settings);

		assertNotNull(result);
		assertEquals(0, result.size());
		
		BigDecimal amount = calculator.calculateOnePercentAmount(user, period, settings);

		assertNotNull(amount);
		assertTrue(BigDecimal.ZERO.compareTo(amount) == 0);
	}
	
	@Test
	public void claculateForFourthQuarter() {
		PaymentPeriod period = new PaymentPeriod(testYear, Quarter.IV);
		TaxCalculationSettings settings = new TaxCalculationSettings();
		settings.setByQuants(true);
		
		List<Tax> result = calculator.calculateOnePercentTaxes(user, period, settings);

		assertNotNull(result);
		assertEquals(0, result.size());
		
		BigDecimal amount = calculator.calculateOnePercentAmount(user, period, settings);

		assertNotNull(amount);
		assertTrue(BigDecimal.ZERO.compareTo(amount) == 0);
	}
}
