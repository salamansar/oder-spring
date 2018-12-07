package org.salamansar.oder.core.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.salamansar.oder.core.component.PaymentPeriodCalculator;
import org.salamansar.oder.core.domain.Income;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
import org.salamansar.oder.core.domain.Tax;
import org.salamansar.oder.core.domain.TaxCalculationSettings;
import org.salamansar.oder.core.domain.TaxCategory;
import org.salamansar.oder.core.domain.User;
import org.salamansar.oder.core.utils.IncomeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Salamansar
 */
@Service
public class OnePercentTaxCalculatorImpl implements OnePercentTaxCalculator {
	private static final BigDecimal PERCENT_RATE = BigDecimal.valueOf(0.01);
	private static final BigDecimal PERCENT_THRESHOLD = BigDecimal.valueOf(300000L);
	@Autowired
	private PaymentPeriodCalculator periodCalcualtor;
	@Autowired
	private IncomeService incomesService;

	@Override
	public List<Tax> calculateOneTaxesPercent(User user, PaymentPeriod period, TaxCalculationSettings settings) {
		if(period.getQuarter() == Quarter.YEAR && !settings.getByQuants()) {
			List<Income> incomes = incomesService.findIncomes(user, period);
			return incomes.stream()
					.collect(Collectors.groupingBy(income -> periodCalcualtor
					.calculatePeriod(income.getIncomeDate())
					.getYear()))
					.entrySet().stream()
					.map(e -> {
						BigDecimal sum = IncomeUtils.incomesSum(e.getValue());
						if (sum.compareTo(PERCENT_THRESHOLD) > 0) {
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
		} else {
			return Collections.emptyList();
		}
	}
	
	
}
