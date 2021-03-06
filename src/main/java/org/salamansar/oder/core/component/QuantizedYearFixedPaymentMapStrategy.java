package org.salamansar.oder.core.component;

import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import org.salamansar.oder.core.domain.FixedPayment;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
import org.salamansar.oder.core.domain.Tax;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Salamansar
 */
@Component("strategy.map.fixedPayment.quantized")
@Scope("prototype")
public class QuantizedYearFixedPaymentMapStrategy implements FixedPaymentMapStrategy {
	
	private FixedPaymentAmountCalculator amountCalculator; //todo: check initialization

	@Override
	public List<Tax> map(FixedPayment payment) {
		BigDecimal paymentAmount = amountCalculator.calculate(payment);
		return EnumSet.allOf(Quarter.class).stream()
				.filter((quarter) -> (quarter != Quarter.YEAR))
				.map((quarter) -> new PaymentPeriod(payment.getYear(), quarter))
				.map((paymentPeriod) -> {
					Tax tax = new Tax(payment.getCategory());
					tax.setPayment(paymentAmount);
					tax.setPeriod(paymentPeriod);
					return tax;
				})
				.collect(Collectors.toList());
	}

	@Override
	public void initialize(PaymentPeriod period, FixedPaymentAmountCalculator amountCalculator) {
		this.amountCalculator = amountCalculator;
	}
	
}
