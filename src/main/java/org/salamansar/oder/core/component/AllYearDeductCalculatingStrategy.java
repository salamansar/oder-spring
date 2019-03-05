package org.salamansar.oder.core.component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.salamansar.oder.core.domain.FixedPayment;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
import org.salamansar.oder.core.domain.TaxCalculationSettings;
import org.salamansar.oder.core.domain.TaxDeduction;
import org.salamansar.oder.core.domain.User;
import org.salamansar.oder.core.service.FixedPaymentService;
import org.salamansar.oder.core.service.OnePercentTaxCalculator;
import org.salamansar.oder.core.utils.PaymentsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Salamansar
 */
@Component("strategy.calculate.deduct.allYear")
public class AllYearDeductCalculatingStrategy implements DeductCalculatingStrategy {
	@Autowired
	private FixedPaymentService fixedPaymentService;
	@Autowired
	private OnePercentTaxCalculator onePercentCalculator;
	
	@Override
	public List<TaxDeduction> calculateDeductions(User user, PaymentPeriod period) {
		if(period.getQuarter() == Quarter.YEAR) {
			List<FixedPayment> payments = fixedPaymentService.findFixedPaymentsByYear(period.getYear());
			BigDecimal fixedPayment = PaymentsUtils.fixedPaymentsSum(payments);
			BigDecimal onePercentPayment = onePercentCalculator.calculateOnePercentAmount(
					user, 
					new PaymentPeriod(period.getYear() - 1, Quarter.YEAR), 
					TaxCalculationSettings.defaults());
			TaxDeduction result = new TaxDeduction();
			result.setPeriod(period);
			result.setDeduction(fixedPayment.add(onePercentPayment));
			return Arrays.asList(result);
		} else {
			return Collections.emptyList();
		}
	}
	
}
