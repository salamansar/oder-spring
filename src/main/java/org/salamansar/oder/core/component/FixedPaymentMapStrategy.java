package org.salamansar.oder.core.component;

import org.salamansar.oder.core.domain.FixedPayment;
import org.salamansar.oder.core.domain.Tax;

/**
 * 
 * @author Salamansar
 */
public interface FixedPaymentMapStrategy {
	Tax map(FixedPayment payment); 
}
