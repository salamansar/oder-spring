package org.salamansar.oder.core.component;

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
@Component("allYearPaymentMapStrategy")
@Scope("prototype")
public class AllYearMapStrategy implements FixedPaymentMapStrategy {

	@Override
	public Tax map(FixedPayment payment) {
        Tax tax = new Tax(payment.getCategory());
        tax.setPayment(payment.getValue());
        tax.setPeriod(new PaymentPeriod(payment.getYear(), Quarter.YEAR));
        return tax;
	}

}
