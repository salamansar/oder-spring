package org.salamansar.oder.core.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.salamansar.oder.core.component.PaymentPeriodCalculator;
import org.salamansar.oder.core.domain.Income;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
import org.salamansar.oder.core.domain.Tax;
import org.salamansar.oder.core.domain.TaxCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Salamansar
 */
@Service
public class TaxCalculatorImpl implements TaxCalculator {
	private static final BigDecimal PERCENT_RATE = BigDecimal.valueOf(0.01);
	private static final BigDecimal PERCENT_THRESHOLD = BigDecimal.valueOf(300000L);
	@Autowired
	private PaymentPeriodCalculator periodCalcualtor;

	@Override
	public List<Tax> calculateOnePercent(List<Income> incomes) {
		return incomes.stream()
				.collect(Collectors.groupingBy(income -> periodCalcualtor
						.calculatePeriod(income.getIncomeDate())
						.getYear()))
				.entrySet().stream()
				.map(e -> {
					BigDecimal sum = incomesSum(e.getValue());
					if(sum.compareTo(PERCENT_THRESHOLD) > 0) {
						Tax tax = new Tax(TaxCategory.PENSION_PERCENT);
						tax.setPeriod(new PaymentPeriod(e.getKey(), Quarter.YEAR));
						tax.setPayment(sum
								.subtract(PERCENT_THRESHOLD)
								.multiply(PERCENT_RATE));
						return tax;
					} else {
						return null;
					}
				})
				.filter(t -> t != null)
				.collect(Collectors.toList());
	}
	
	private BigDecimal incomesSum(List<Income> incomes) {
		return incomes.stream()
				.map(income -> income.getAmount())
				.reduce(BigDecimal.ZERO,
						(accum, amount) -> accum.add(amount));
	}
	
}
