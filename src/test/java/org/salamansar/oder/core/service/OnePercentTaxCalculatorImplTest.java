package org.salamansar.oder.core.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.envbuild.generator.RandomGenerator;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.salamansar.oder.core.PaymentPeriodCalculatorInitializer;
import org.salamansar.oder.core.component.PaymentPeriodCalculator;
import org.salamansar.oder.core.domain.Income;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
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
	@Mock
	private PaymentPeriodCalculator periodCalcualtor;
	@InjectMocks
	private OnePercentTaxCalculatorImpl calculator = new OnePercentTaxCalculatorImpl();
	private RandomGenerator generator = new RandomGenerator();
	private PaymentPeriodCalculatorInitializer periodCalcData;
	private User user;

	@Before
	public void setUp() {
		periodCalcData = PaymentPeriodCalculatorInitializer.init(periodCalcualtor);
		Income income1 = new Income();
		income1.setIncomeDate(periodCalcData.getFirstQuarter());
		income1.setAmount(BigDecimal.valueOf(100000.50));
		Income income2 = new Income();
		income2.setIncomeDate(periodCalcData.getSecondQuarter());
		income2.setAmount(BigDecimal.valueOf(250000.50));
		Income income3 = new Income();
		income3.setIncomeDate(periodCalcData.getFirstQuarter());
		income3.setAmount(BigDecimal.valueOf(25000.25));
		Income income4 = new Income();
		income4.setIncomeDate(periodCalcData.getNextYearFirstQuarter());
		income4.setAmount(BigDecimal.valueOf(256000.10));
		user = generator.generate(User.class);
		when(incomesService.findIncomes(same(user), any(PaymentPeriod.class)))
				.thenReturn(Arrays.asList(income1, income2, income3, income4));
	}

	@Test
	public void claculatePercent() {
		PaymentPeriod period = new PaymentPeriod(2018, Quarter.YEAR);
		TaxCalculationSettings settings = new TaxCalculationSettings();
		
		List<Tax> result = calculator.calculateOnePercentTaxes(user, period, settings);

		assertNotNull(result);
		assertEquals(1, result.size());
		Optional<Tax> tax = result.stream()
				.filter(t -> new PaymentPeriod(2018, Quarter.YEAR).equals(t.getPeriod()))
				.findFirst();
		assertTrue(tax.isPresent());
		assertEquals(TaxCategory.PENSION_PERCENT, tax.get().getCatgory());
		assertTrue(BigDecimal.valueOf(750.0125).compareTo(tax.get().getPayment()) == 0);
	}
	
	@Test
	public void claculatePercentForNotEnoughAmount() {
		PaymentPeriod period = new PaymentPeriod(2018, Quarter.YEAR);
		TaxCalculationSettings settings = new TaxCalculationSettings();
		Income income1 = new Income();
		income1.setIncomeDate(periodCalcData.getFirstQuarter());
		income1.setAmount(BigDecimal.valueOf(10000.50));
		Income income2 = new Income();
		income2.setIncomeDate(periodCalcData.getSecondQuarter());
		income2.setAmount(BigDecimal.valueOf(250000.50));
		when(incomesService.findIncomes(same(user), any(PaymentPeriod.class)))
				.thenReturn(Arrays.asList(income1, income2));
		
		List<Tax> result = calculator.calculateOnePercentTaxes(user, period, settings);

		assertNotNull(result);
		assertEquals(0, result.size());		
	}
	
	@Test
	public void claculatePercentForEmptyIncomes() {
		PaymentPeriod period = new PaymentPeriod(2018, Quarter.YEAR);
		TaxCalculationSettings settings = new TaxCalculationSettings();
		when(incomesService.findIncomes(same(user), any(PaymentPeriod.class)))
				.thenReturn(Collections.emptyList());
		
		List<Tax> result = calculator.calculateOnePercentTaxes(user, period, settings);

		assertNotNull(result);
		assertEquals(0, result.size());
	}
	
	@Test
	public void claculateForQuantized() {
		PaymentPeriod period = new PaymentPeriod(2018, Quarter.YEAR);
		TaxCalculationSettings settings = new TaxCalculationSettings();
		settings.setByQuants(true);
		
		List<Tax> result = calculator.calculateOnePercentTaxes(user, period, settings);

		assertNotNull(result);
		assertEquals(0, result.size());
	}
	
	@Test
	public void claculateForQuarter() {
		PaymentPeriod period = new PaymentPeriod(2018, Quarter.III);
		TaxCalculationSettings settings = new TaxCalculationSettings();
		settings.setByQuants(true);
		
		List<Tax> result = calculator.calculateOnePercentTaxes(user, period, settings);

		assertNotNull(result);
		assertEquals(0, result.size());
	}

	@Test
	public void calculatePercentAmount() {
		PaymentPeriod period = new PaymentPeriod(2018, Quarter.YEAR);
		TaxCalculationSettings settings = new TaxCalculationSettings();

		BigDecimal result = calculator.calculateOnePercentAmount(user, period, settings);

		assertNotNull(result);		
		assertTrue(BigDecimal.valueOf(750.0125).compareTo(result) == 0);
	}
	
	@Test
	public void claculateAmountForNotEnoughAmount() {
		PaymentPeriod period = new PaymentPeriod(2018, Quarter.YEAR);
		TaxCalculationSettings settings = new TaxCalculationSettings();
		Income income1 = new Income();
		income1.setIncomeDate(periodCalcData.getFirstQuarter());
		income1.setAmount(BigDecimal.valueOf(10000.50));
		Income income2 = new Income();
		income2.setIncomeDate(periodCalcData.getSecondQuarter());
		income2.setAmount(BigDecimal.valueOf(250000.50));
		when(incomesService.findIncomes(same(user), any(PaymentPeriod.class)))
				.thenReturn(Arrays.asList(income1, income2));

		BigDecimal result = calculator.calculateOnePercentAmount(user, period, settings);

		assertNotNull(result);
		assertTrue(BigDecimal.ZERO.compareTo(result) == 0);
	}

	@Test
	public void claculateAmountForEmptyIncomes() {
		PaymentPeriod period = new PaymentPeriod(2018, Quarter.YEAR);
		TaxCalculationSettings settings = new TaxCalculationSettings();
		when(incomesService.findIncomes(same(user), any(PaymentPeriod.class)))
				.thenReturn(Collections.emptyList());

		BigDecimal result = calculator.calculateOnePercentAmount(user, period, settings);

		assertNotNull(result);
		assertTrue(BigDecimal.ZERO.compareTo(result) == 0);
	}

		
	@Test
	public void claculateAmountForQuantized() {
		PaymentPeriod period = new PaymentPeriod(2018, Quarter.YEAR);
		TaxCalculationSettings settings = new TaxCalculationSettings();
		settings.setByQuants(true);

		BigDecimal result = calculator.calculateOnePercentAmount(user, period, settings);

		assertNotNull(result);
		assertTrue(BigDecimal.ZERO.compareTo(result) == 0);
	}

	@Test
	public void claculateAmountForQuarter() {
		PaymentPeriod period = new PaymentPeriod(2018, Quarter.III);
		TaxCalculationSettings settings = new TaxCalculationSettings();
		settings.setByQuants(true);

		BigDecimal result = calculator.calculateOnePercentAmount(user, period, settings);

		assertNotNull(result);
		assertTrue(BigDecimal.ZERO.compareTo(result) == 0);
	}
}
