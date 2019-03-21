package org.salamansar.oder.core.component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.salamansar.oder.core.domain.FixedPayment;

/**
 *
 * @author Salamansar
 */
public class RoundingFixedPaymentWrapper implements FixedPaymentAmountCalculator {
	private FixedPaymentAmountCalculator delegate;

	public RoundingFixedPaymentWrapper(FixedPaymentAmountCalculator delegate) {
		this.delegate = delegate;
	}

	@Override
	public BigDecimal calculate(FixedPayment domain) {
		BigDecimal value = delegate.calculate(domain);
		return value == null ? null : value.setScale(0, RoundingMode.CEILING);
	}

}
