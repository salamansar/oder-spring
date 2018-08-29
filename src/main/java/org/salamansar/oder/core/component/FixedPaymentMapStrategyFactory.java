package org.salamansar.oder.core.component;

import org.salamansar.oder.core.domain.PaymentPeriod;

/**
 *
 * @author Salamansar
 */
//todo: implement
public interface FixedPaymentMapStrategyFactory {
	FixedPaymentMapStrategy getStartegy(PaymentPeriod period);
}
