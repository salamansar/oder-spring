package org.salamansar.oder.core.component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.salamansar.oder.core.domain.Income;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
import org.salamansar.oder.core.domain.QuarterIncome;
import org.salamansar.oder.core.utils.IncomeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Salamansar
 */
@Component("strategy.map.quarterIncome.summarized")
public class SummarizedQuarterIncomeMapStrategy implements QuarterIncomeMapStrategy {
	@Autowired
	private PaymentPeriodCalculator periodCalcualtor;

	@Override
	public List<QuarterIncome> map(List<Income> incomes) {
		return incomes.stream()
				.collect(Collectors.groupingBy(i -> periodCalcualtor.calculatePeriod(i.getIncomeDate()).getYear()))
				.entrySet().stream()
				.map(e -> {
					BigDecimal incomeAmount = IncomeUtils.incomesSum(e.getValue());
					QuarterIncome income = new QuarterIncome();
					income.setPeriod(new PaymentPeriod(e.getKey(), Quarter.YEAR));
					income.setIncomeAmount(incomeAmount);
					return income;
				})
				.collect(Collectors.toList());
	}
	
}
