package org.salamansar.oder.core.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
import org.salamansar.oder.core.domain.QuarterIncome;
import org.salamansar.oder.core.domain.Tax;
import org.salamansar.oder.core.domain.TaxCalculationSettings;
import org.salamansar.oder.core.domain.TaxCategory;
import org.salamansar.oder.core.domain.User;
import org.salamansar.oder.core.utils.PaymentsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Salamansar
 */
@Service
public class OnePercentTaxCalculatorImpl implements OnePercentTaxCalculator {
	private static final BigDecimal PERCENT_RATE = BigDecimal.valueOf(0.01);
	private static final BigDecimal PERCENT_THRESHOLD = BigDecimal.valueOf(300000L);
	@Autowired
	private IncomeService incomesService;

	@Override
	public List<Tax> calculateOnePercentTaxes(User user, PaymentPeriod period, TaxCalculationSettings settings) {
		Optional<PaymentPeriodInfo> calcPeriod = getCalcPeriodInfo(period, settings);
		if(calcPeriod.isPresent()) {
			QuarterIncome periodIncome = incomesService.findSingleIncome(user, calcPeriod.get().calcPeriod);
			if(periodIncome != null 
					&& periodIncome.getIncomeAmount().compareTo(PERCENT_THRESHOLD) > 0) {
					Tax tax = new Tax(TaxCategory.PENSION_PERCENT);
					tax.setPeriod(calcPeriod.get().taxPeriod);
					tax.setPayment(periodIncome.getIncomeAmount()
							.subtract(PERCENT_THRESHOLD)
							.multiply(PERCENT_RATE));
					return Arrays.asList(tax);
			}
		} 
		return Collections.emptyList();
	}
	
	private Optional<PaymentPeriodInfo> getCalcPeriodInfo(PaymentPeriod srcPeriod, TaxCalculationSettings settings) {
		if(srcPeriod.getQuarter() == Quarter.YEAR && !settings.getByQuants()) {
			return Optional.of(new PaymentPeriodInfo(srcPeriod, srcPeriod));
		} else if(srcPeriod.getQuarter() == Quarter.YEAR && settings.getByQuants() 
				|| srcPeriod.getQuarter() == Quarter.I) {
			return Optional.of(new PaymentPeriodInfo(
					srcPeriod.previousYear().asQuarter(Quarter.YEAR), 
					srcPeriod.asQuarter(Quarter.I)
				)
			);
		} else {
			return Optional.empty();
		}
	}
	
	@Override
	public BigDecimal calculateOnePercentAmount(User user, PaymentPeriod period, TaxCalculationSettings settings) {
		if(period.getQuarter() == Quarter.YEAR && settings.getByQuants()) {
			return BigDecimal.ZERO;
		}
		List<Tax> taxes = calculateOnePercentTaxes(user, period, settings);
		return PaymentsUtils.taxesSum(taxes);
	}
	
	private static class PaymentPeriodInfo {
		private PaymentPeriod calcPeriod;
		private PaymentPeriod taxPeriod;

		public PaymentPeriodInfo(PaymentPeriod calcPeriod, PaymentPeriod taxPeriod) {
			this.calcPeriod = calcPeriod;
			this.taxPeriod = taxPeriod;
		}
	}
}
