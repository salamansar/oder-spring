package org.salamansar.oder.core.utils;

import java.math.BigDecimal;
import java.util.List;
import org.salamansar.oder.core.domain.Income;

/**
 *
 * @author Salamansar
 */
public class IncomeUtils {
	
	public static BigDecimal incomesSum(List<Income> incomes) {
		return incomes.stream()
				.map(income -> income.getAmount())
				.reduce(BigDecimal.ZERO,
						(accum, amount) -> accum.add(amount));
	}
	
}
