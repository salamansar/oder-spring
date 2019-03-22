package org.salamansar.oder.core.component;

import org.salamansar.oder.core.domain.TaxCalculationSettings;

/**
 *
 * @author Salamansar
 */
public interface DeductCombineStrategyFactory {
	DeductCombineStrategy getStrategy(TaxCalculationSettings settings);
}
