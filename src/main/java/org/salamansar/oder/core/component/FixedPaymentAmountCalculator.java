package org.salamansar.oder.core.component;

import java.math.BigDecimal;
import org.salamansar.oder.core.domain.FixedPayment;

/**
 *
 * @author Salamansar
 */
public interface FixedPaymentAmountCalculator {
	BigDecimal calculate(FixedPayment domain);
}
