package org.salamansar.oder.core.component;

import java.math.BigDecimal;
import java.util.List;
import org.salamansar.oder.core.domain.QuarterIncome;
import org.salamansar.oder.core.utils.PaymentsUtils;
import org.springframework.stereotype.Component;

/**
 *
 * @author Salamansar
 */
@Component("strategy.calculate.taxAmount.simple")
public class SimpleTaxAmountCalculator implements TaxAmountCalculator {
	private static final BigDecimal TAX_RATE = BigDecimal.valueOf(0.06); //todo: must be replaced for calculation, since rate may be different
	
	@Override
	public BigDecimal calculateTax(List<QuarterIncome> incomes) {
		return calculate(PaymentsUtils.summarize(incomes, v -> v.getIncomeAmount()));
	}

	@Override
	public BigDecimal calculateTax(QuarterIncome income) {
		return income.getIncomeAmount() == null ? null : calculate(income.getIncomeAmount());
	}
	
	private BigDecimal calculate(BigDecimal amount) {
		return amount.multiply(TAX_RATE);
	}
}
