package org.salamansar.oder.core.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.salamansar.oder.core.component.PaymentPeriodCalculator;
import org.salamansar.oder.core.domain.Income;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
import org.salamansar.oder.core.domain.Tax;
import org.salamansar.oder.core.domain.TaxCategory;

/**
 *
 * @author Salamansar
 */
@RunWith(MockitoJUnitRunner.class)
public class TaxCalculatorImplTest {

	@Mock
	private PaymentPeriodCalculator periodCalcualtor;
	@InjectMocks
	private TaxCalculatorImpl calculator = new TaxCalculatorImpl();

	private LocalDate firstQuarter = LocalDate.of(2018, Month.FEBRUARY, 5);
	private LocalDate secondQuarter = LocalDate.of(2018, Month.MAY, 5);
	private LocalDate nextYearFirstQuarter = LocalDate.of(2019, Month.JANUARY, 5);
	private LocalDate nextYearSecondQuarter = LocalDate.of(2019, Month.APRIL, 5);

	@Before
	public void setUp() {
		when(periodCalcualtor.calculatePeriod(eq(firstQuarter)))
				.thenReturn(new PaymentPeriod(2018, Quarter.I));
		when(periodCalcualtor.calculatePeriod(eq(secondQuarter)))
				.thenReturn(new PaymentPeriod(2018, Quarter.II));
		when(periodCalcualtor.calculatePeriod(eq(nextYearFirstQuarter)))
				.thenReturn(new PaymentPeriod(2019, Quarter.I));
		when(periodCalcualtor.calculatePeriod(eq(nextYearSecondQuarter)))
				.thenReturn(new PaymentPeriod(2019, Quarter.II));
	}

	@Test
	public void calculateIncomes() {
		Income income1 = new Income();
		income1.setIncomeDate(firstQuarter);
		income1.setAmount(BigDecimal.valueOf(100.50));
		Income income2 = new Income();
		income2.setIncomeDate(secondQuarter);
		income2.setAmount(BigDecimal.valueOf(50.50));
		Income income3 = new Income();
		income3.setIncomeDate(firstQuarter);
		income3.setAmount(BigDecimal.valueOf(25.25));

		List<Tax> result = calculator.calculateIncomeTaxes(Arrays.asList(income1, income2, income3));

		assertNotNull(result);
		assertEquals(2, result.size());
		Optional<Tax> tax = result.stream()
				.filter(t -> new PaymentPeriod(2018, Quarter.I).equals(t.getPeriod()))
				.findFirst();
		assertTrue(tax.isPresent());
		assertEquals(TaxCategory.INCOME_TAX, tax.get().getCatgory());
		assertTrue(BigDecimal.valueOf(7.545).compareTo(tax.get().getPayment()) == 0);
		tax = result.stream()
				.filter(t -> new PaymentPeriod(2018, Quarter.II).equals(t.getPeriod()))
				.findFirst();
		assertTrue(tax.isPresent());
		assertEquals(TaxCategory.INCOME_TAX, tax.get().getCatgory());
		assertTrue(BigDecimal.valueOf(3.03).compareTo(tax.get().getPayment()) == 0);
	}

	@Test
	public void claculatePercent() {
		Income income1 = new Income();
		income1.setIncomeDate(firstQuarter);
		income1.setAmount(BigDecimal.valueOf(180000));
		Income income2 = new Income();
		income2.setIncomeDate(nextYearFirstQuarter);
		income2.setAmount(BigDecimal.valueOf(70000));
		Income income3 = new Income();
		income3.setIncomeDate(secondQuarter);
		income3.setAmount(BigDecimal.valueOf(130000));
		Income income4 = new Income();
		income4.setIncomeDate(nextYearSecondQuarter);
		income4.setAmount(BigDecimal.valueOf(230000));

		List<Tax> result = calculator.calculateOnePercent(Arrays.asList(income1, income2, income3, income4));

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(new PaymentPeriod(2018, Quarter.YEAR), result.get(0).getPeriod());
		assertEquals(TaxCategory.PENSION_PERCENT, result.get(0).getCatgory());
		assertTrue(result.get(0).getPayment().toString(), BigDecimal.valueOf(100).compareTo(result.get(0).getPayment()) == 0);
	}

}
