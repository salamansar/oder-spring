package org.salamansar.oder.module.payments.dto;

import java.math.BigDecimal;
import lombok.Data;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;

/**
 *
 * @author Salamansar
 */
@Data
public class TaxRowDto {
	private BigDecimal incomesAmount;
	private BigDecimal incomesTaxAmount;
	private BigDecimal incomesDeductedTaxAmount;
	private BigDecimal onePercentTaxAmount;
	private BigDecimal healthInsuranceTaxAmount;
	private BigDecimal pensionTaxAmount;
	private PaymentPeriod paymentPeriod;
	
	public String getPaymentPeriodMessage() {
		if(paymentPeriod == null) {
			return null;
		}
		if(paymentPeriod.getQuarter() == Quarter.YEAR) {
			return "Год";
		} else {
			return paymentPeriod.getQuarter().name();
		}
	}
	
	public BigDecimal getFixedPaymentsTaxAmount() {
		if(healthInsuranceTaxAmount == null && pensionTaxAmount == null) {
			return null;
		}
		return valueOrZero(healthInsuranceTaxAmount).add(valueOrZero(pensionTaxAmount));
	}

	public BigDecimal getSummarizedTaxAmount() {
		BigDecimal fixedPayment = getFixedPaymentsTaxAmount();
		if(fixedPayment == null && incomesTaxAmount == null) {
			return null;
		}
		return valueOrZero(fixedPayment).add(valueOrZero(incomesTaxAmount));
	}
	
	private BigDecimal valueOrZero(BigDecimal value) {
		return value == null ? BigDecimal.ZERO : value;
	}
	
	public BigDecimal getSummarizedDeductedTaxAmount() {
		BigDecimal fixedPayment = getFixedPaymentsTaxAmount();
		if (fixedPayment == null && incomesDeductedTaxAmount == null) {
			return null;
		}
		return valueOrZero(fixedPayment).add(valueOrZero(incomesDeductedTaxAmount));
	}
	
}
