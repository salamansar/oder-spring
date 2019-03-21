package org.salamansar.oder.core.component;

import java.util.Arrays;
import java.util.List;
import org.salamansar.oder.core.domain.FixedPayment;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Tax;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Salamansar
 */
@Component("strategy.map.fixedPayment.single")
@Scope("prototype")
public class SingleMonthFixedPaymentStrategy implements FixedPaymentMapStrategy {
    private PaymentPeriod calculationPeriod; //todo: check initialization
	private FixedPaymentAmountCalculator amountCalculator; //todo: check initialization

	@Override
	public List<Tax> map(FixedPayment payment) {
        Tax tax = new Tax(payment.getCategory());
        tax.setPeriod(calculationPeriod);
        tax.setPayment(amountCalculator.calculate(payment));
        return Arrays.asList(tax);
	}

    @Override
    public void initialize(PaymentPeriod period, FixedPaymentAmountCalculator amountCalculator) {
        this.calculationPeriod = period;
		this.amountCalculator = amountCalculator;
    }
    
}
