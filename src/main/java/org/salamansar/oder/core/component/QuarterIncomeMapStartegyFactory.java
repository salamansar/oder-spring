package org.salamansar.oder.core.component;

import org.salamansar.oder.core.domain.PaymentPeriod;

/**
 *
 * @author Salamansar
 */
public interface QuarterIncomeMapStartegyFactory {
	
	QuarterIncomeMapStrategy getStrategy(PaymentPeriod period, boolean byQuants);
	
}
