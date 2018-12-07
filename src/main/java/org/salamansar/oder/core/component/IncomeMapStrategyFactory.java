package org.salamansar.oder.core.component;

import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.TaxCalculationSettings;

/**
 *
 * @author Salamansar
 */
public interface IncomeMapStrategyFactory {
	
	IncomeMapStrategy getStrategy(PaymentPeriod period, TaxCalculationSettings settings); 
			
}
