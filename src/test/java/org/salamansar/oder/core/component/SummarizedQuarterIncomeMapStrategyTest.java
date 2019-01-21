package org.salamansar.oder.core.component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.salamansar.oder.core.PaymentPeriodCalculatorInitializer;
import org.salamansar.oder.core.domain.Income;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
import org.salamansar.oder.core.domain.QuarterIncome;

/**
 *
 * @author Salamansar
 */
@RunWith(MockitoJUnitRunner.class)
public class SummarizedQuarterIncomeMapStrategyTest {
	@Mock
	private PaymentPeriodCalculator periodCalcualtor;
	@InjectMocks
	private SummarizedQuarterIncomeMapStrategy strategy = new SummarizedQuarterIncomeMapStrategy();
	
	private PaymentPeriodCalculatorInitializer periodCalcData;

	@Before
	public void setUp() {
		periodCalcData = PaymentPeriodCalculatorInitializer.init(periodCalcualtor);
	}

	@Test
	public void calculateQuarterIncomes() {
		Income income1 = new Income();
		income1.setIncomeDate(periodCalcData.getFirstQuarter());
		income1.setAmount(BigDecimal.valueOf(100.50));
		Income income2 = new Income();
		income2.setIncomeDate(periodCalcData.getSecondQuarter());
		income2.setAmount(BigDecimal.valueOf(50.50));
		Income income3 = new Income();
		income3.setIncomeDate(periodCalcData.getFirstQuarter());
		income3.setAmount(BigDecimal.valueOf(25.25));
		Income income4 = new Income();
		income4.setIncomeDate(periodCalcData.getNextYearFirstQuarter());
		income4.setAmount(BigDecimal.valueOf(256.10));

		List<QuarterIncome> result = strategy.map(Arrays.asList(income1, income2, income3, income4));

		assertNotNull(result);
		assertEquals(2, result.size());
		Optional<QuarterIncome> quarterIncome = result.stream()
				.filter(t -> new PaymentPeriod(2018, Quarter.YEAR).equals(t.getPeriod()))
				.findFirst();
		assertTrue(quarterIncome.isPresent());
		assertTrue(BigDecimal.valueOf(176.25).compareTo(quarterIncome.get().getIncomeAmount()) == 0);
		quarterIncome = result.stream()
				.filter(t -> new PaymentPeriod(2019, Quarter.YEAR).equals(t.getPeriod()))
				.findFirst();
		assertTrue(quarterIncome.isPresent());
		assertTrue(BigDecimal.valueOf(256.10).compareTo(quarterIncome.get().getIncomeAmount()) == 0);
	}
	
}
