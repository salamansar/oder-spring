package org.salamansar.oder.core.component;

import java.math.BigDecimal;
import java.util.List;
import org.salamansar.oder.core.domain.QuarterIncome;

/**
 *
 * @author Salamansar
 */
public interface TaxAmountCalculator {
	BigDecimal calculateTax(List<QuarterIncome> incomes);
	
	BigDecimal calculateTax(QuarterIncome income);
}
