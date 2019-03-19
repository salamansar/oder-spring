package org.salamansar.oder.core.component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import org.salamansar.oder.core.domain.QuarterIncome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Salamansar
 */
@Component("strategy.calculate.taxAmount.rounded")
public class RoundingTaxAmountCalculator implements TaxAmountCalculator {
	
	@Autowired
	private SimpleTaxAmountCalculator simpleCalculator;

	@Override
	public BigDecimal calculateTax(List<QuarterIncome> incomes) {
		return calculate(simpleCalculator.calculateTax(incomes));
	}

	@Override
	public BigDecimal calculateTax(QuarterIncome income) {
		return calculate(simpleCalculator.calculateTax(income));
	}
	
	private BigDecimal calculate(BigDecimal amount) {
		return amount == null ? null : amount.setScale(0, RoundingMode.CEILING);
	}
}
