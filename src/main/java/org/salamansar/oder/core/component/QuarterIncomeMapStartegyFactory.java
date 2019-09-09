package org.salamansar.oder.core.component;

import org.salamansar.oder.core.domain.Quarter;

/**
 *
 * @author Salamansar
 */
public interface QuarterIncomeMapStartegyFactory {
	
	QuarterIncomeMapStrategy getStrategy(Quarter quarter, boolean byQuants);
	
}
