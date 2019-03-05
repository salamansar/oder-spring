package org.salamansar.oder.core.component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
import org.salamansar.oder.core.domain.Tax;
import org.salamansar.oder.core.domain.TaxCalculationSettings;
import org.salamansar.oder.core.domain.TaxDeduction;
import org.salamansar.oder.core.domain.User;
import org.salamansar.oder.core.service.FixedPaymentTaxCalculator;
import org.salamansar.oder.core.service.OnePercentTaxCalculator;
import org.salamansar.oder.core.utils.PaymentsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Salamansar
 */
@Component("strategy.calculate.deduct.single")
public class SingleMonthDeductCalculationStrategy implements DeductCalculatingStrategy {
	@Autowired
	private FixedPaymentTaxCalculator fixedPaymentCalculator;
	@Autowired
	private OnePercentTaxCalculator onePercentCalculator;
	
	@Override
	public List<TaxDeduction> calculateDeductions(User user, PaymentPeriod period) {
		if(period.getQuarter() != Quarter.YEAR) {
			List<Tax> taxes = fixedPaymentCalculator.calculateFixedPayments(period, TaxCalculationSettings.defaults());
			BigDecimal amount = calculateDeductionAmount(taxes, user, period);
			if(BigDecimal.ZERO.compareTo(amount) < 0) {
				TaxDeduction deduction = new TaxDeduction();
				deduction.setPeriod(period);
				deduction.setDeduction(amount);
				return Arrays.asList(deduction);
			} 
		}
		return Collections.emptyList();
	}

	private BigDecimal calculateDeductionAmount(List<Tax> fixedTaxes, User user, PaymentPeriod period) {
		BigDecimal fixedPayments = PaymentsUtils.taxesSum(fixedTaxes);
		if(period.getQuarter() == Quarter.I) {
			BigDecimal additionalPayment = onePercentCalculator.calculateOnePercentAmount(
					user, 
					new PaymentPeriod(period.getYear() - 1, Quarter.YEAR), 
					TaxCalculationSettings.defaults());
			return fixedPayments.add(additionalPayment);
		} else {
			return fixedPayments;
		}
	}
}
