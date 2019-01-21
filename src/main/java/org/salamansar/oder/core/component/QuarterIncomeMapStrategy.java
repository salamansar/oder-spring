package org.salamansar.oder.core.component;

import java.util.List;
import org.salamansar.oder.core.domain.Income;
import org.salamansar.oder.core.domain.QuarterIncome;

/**
 *
 * @author Salamansar
 */
public interface QuarterIncomeMapStrategy {
	
	List<QuarterIncome> map(List<Income> incomes);
			
}
