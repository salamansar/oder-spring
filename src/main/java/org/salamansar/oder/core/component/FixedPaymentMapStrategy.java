package org.salamansar.oder.core.component;

import java.util.List;
import org.salamansar.oder.core.domain.FixedPayment;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Tax;

/**
 * 
 * @author Salamansar
 */
public interface FixedPaymentMapStrategy {
	List<Tax> map(FixedPayment payment); 
	
	void initialize(PaymentPeriod period, FixedPaymentAmountCalculator amountCalculator);
}
