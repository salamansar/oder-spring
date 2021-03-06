package org.salamansar.oder.core.component;

import java.util.Arrays;
import java.util.List;
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
@Component("strategy.map.fixedPayment.allYear")
@Scope("prototype")
public class AllYearFixedPaymentMapStrategy implements FixedPaymentMapStrategy {
	
	private FixedPaymentAmountCalculator amountCalculator; //todo: check initialization

	@Override
	public List<Tax> map(FixedPayment payment) {
        Tax tax = new Tax(payment.getCategory());
        tax.setPayment(amountCalculator.calculate(payment));
        tax.setPeriod(new PaymentPeriod(payment.getYear(), Quarter.YEAR));
        return Arrays.asList(tax);
	}
	
	@Override
	public void initialize(PaymentPeriod period, FixedPaymentAmountCalculator amountCalculator) {
		this.amountCalculator = amountCalculator;
	}
	

}
