package org.salamansar.oder.core.component;

import java.math.BigDecimal;
import org.salamansar.oder.core.domain.FixedPayment;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Tax;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Salamansar
 */
@Component("singleMonthPaymentMapStrategy")
@Scope("prototype")
public class SingleMonthStrategy implements FixedPaymentMapStrategy, PeriodInitialized {
    private PaymentPeriod calculationPeriod;

	@Override
	public Tax map(FixedPayment payment) {
        Tax tax = new Tax(payment.getCategory());
        tax.setPeriod(calculationPeriod);
        tax.setPayment(payment.getValue().divide(BigDecimal.valueOf(4))); //todo: do division with rounding rules
        return tax;
	}

    @Override
    public void init(PaymentPeriod period) {
        calculationPeriod = period;
    }
    
}
