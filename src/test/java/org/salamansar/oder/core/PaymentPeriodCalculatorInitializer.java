package org.salamansar.oder.core;

import java.time.LocalDate;
import java.time.Month;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import org.salamansar.oder.core.component.PaymentPeriodCalculator;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;

/**
 *
 * @author Salamansar
 */
public class PaymentPeriodCalculatorInitializer {
	private LocalDate firstQuarter = LocalDate.of(2018, Month.FEBRUARY, 5);
	private LocalDate secondQuarter = LocalDate.of(2018, Month.MAY, 5);
	private LocalDate nextYearFirstQuarter = LocalDate.of(2019, Month.JANUARY, 5);
	private LocalDate nextYearSecondQuarter = LocalDate.of(2019, Month.APRIL, 5);

	private PaymentPeriodCalculatorInitializer() {
	}

	public LocalDate getFirstQuarter() {
		return firstQuarter;
	}

	public LocalDate getSecondQuarter() {
		return secondQuarter;
	}

	public LocalDate getNextYearFirstQuarter() {
		return nextYearFirstQuarter;
	}

	public LocalDate getNextYearSecondQuarter() {
		return nextYearSecondQuarter;
	}
	
	public static PaymentPeriodCalculatorInitializer init(PaymentPeriodCalculator periodCalcualtor) {
		PaymentPeriodCalculatorInitializer data = new PaymentPeriodCalculatorInitializer();
		when(periodCalcualtor.calculatePeriod(eq(data.firstQuarter)))
				.thenReturn(new PaymentPeriod(2018, Quarter.I));
		when(periodCalcualtor.calculatePeriod(eq(data.secondQuarter)))
				.thenReturn(new PaymentPeriod(2018, Quarter.II));
		when(periodCalcualtor.calculatePeriod(eq(data.nextYearFirstQuarter)))
				.thenReturn(new PaymentPeriod(2019, Quarter.I));
		when(periodCalcualtor.calculatePeriod(eq(data.nextYearSecondQuarter)))
				.thenReturn(new PaymentPeriod(2019, Quarter.II));
		return data;
	}
	
}
