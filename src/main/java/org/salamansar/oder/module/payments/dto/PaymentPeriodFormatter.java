package org.salamansar.oder.module.payments.dto;

import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;

/**
 *
 * @author Salamansar
 */
public class PaymentPeriodFormatter {
	private PaymentPeriod paymentPeriod;

	public PaymentPeriodFormatter(PaymentPeriod paymentPeriod) {
		this.paymentPeriod = paymentPeriod;
	}
	
	public String getFormattedMessage() {
		if(paymentPeriod.getQuarter() == Quarter.YEAR) {
			return String.format("%d год", paymentPeriod.getYear());
		} else {
			return String.format("%s квартал %d года", paymentPeriod.getQuarter().name(), paymentPeriod.getYear());
		}
	}

	public Integer getYear() {
		return paymentPeriod.getYear();
	}

	public Integer getQuarter() {
		return paymentPeriod.getQuarter().asNumber();
	}
	
}
