package org.salamansar.oder.core.component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.salamansar.oder.core.domain.Income;
import org.salamansar.oder.core.domain.QuarterIncome;
import org.salamansar.oder.core.utils.PaymentsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Salamansar
 */
@Component("strategy.map.quarterIncome.quantized")
public class QuantizedQuarterIncomeMapStrategy implements QuarterIncomeMapStrategy {
	@Autowired
	private PaymentPeriodCalculator paymentPeriodCalculator;
	
	@Override
	public List<QuarterIncome> map(List<Income> incomes) {
		return incomes.stream()
				.collect(Collectors.groupingBy(income -> paymentPeriodCalculator.calculatePeriod(income.getIncomeDate())))
				.entrySet().stream()
				.map(e -> {
					BigDecimal incomeAmount = PaymentsUtils.incomesSum(e.getValue());
					QuarterIncome income = new QuarterIncome();
					income.setPeriod(e.getKey());
					income.setIncomeAmount(incomeAmount);
					return income;
				})
				.collect(Collectors.toList());
	}
	
}
