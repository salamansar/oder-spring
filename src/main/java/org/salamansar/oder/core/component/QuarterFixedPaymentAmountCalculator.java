package org.salamansar.oder.core.component;

import java.math.BigDecimal;
import org.salamansar.oder.core.domain.FixedPayment;

/**
 *
 * @author Salamansar
 */
public class QuarterFixedPaymentAmountCalculator implements FixedPaymentAmountCalculator {

	@Override
	public BigDecimal calculate(FixedPayment domain) {
		return domain == null || domain.getValue() == null
				? null
				: domain.getValue().divide(BigDecimal.valueOf(4));
	}
	
}
