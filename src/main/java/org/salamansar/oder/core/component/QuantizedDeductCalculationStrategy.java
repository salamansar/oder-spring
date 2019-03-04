package org.salamansar.oder.core.component;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
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
@Component("strategy.calculate.deduct.quantized")
public class QuantizedDeductCalculationStrategy implements DeductCalculatingStrategy {
	@Autowired
	private FixedPaymentTaxCalculator fixedPaymentCalculator;
	@Autowired
	private OnePercentTaxCalculator onePercentCalculator;
	
	@Override
	public List<TaxDeduction> calculateDeductions(User user, PaymentPeriod period) {
		if(period.getQuarter() == Quarter.YEAR) {
			BigDecimal onePercentPayment = onePercentCalculator.calculateOnePercentAmount(
					user,
					period.asYear(period.getYear() - 1),
					TaxCalculationSettings.defaults());
			List<Tax> taxes = fixedPaymentCalculator.calculateFixedPayments(period, TaxCalculationSettings.defaults().splitByQuants(true));
			Map<Quarter, TaxDeduction> deductions = taxes.stream()
					.collect(Collectors.groupingBy(Tax::getPeriod))
					.entrySet().stream()
					.map(e -> {
						TaxDeduction deduction = new TaxDeduction();
						deduction.setPeriod(e.getKey());
						deduction.setDeduction(PaymentsUtils.taxesSum(e.getValue()));
						return deduction;
					})
					.collect(Collectors.toMap(d -> d.getPeriod().getQuarter(), Function.identity()));
			mergeWithOnePercent(deductions, onePercentPayment, period);
			return deductions.values().stream().collect(Collectors.toList());
		} else {
			return Collections.emptyList();
		}
	}

	private void mergeWithOnePercent(Map<Quarter, TaxDeduction> deductions, BigDecimal onePercentPayment, PaymentPeriod period) {
		if(BigDecimal.ZERO.equals(onePercentPayment)) {
			return;
		}
		
		TaxDeduction firstQuarter = deductions.get(Quarter.I);
		if(firstQuarter == null) {
			deductions.put(Quarter.I, new TaxDeduction(period.asQuarter(Quarter.I), onePercentPayment));
		} else {
			firstQuarter.setDeduction(firstQuarter.getDeduction().add(onePercentPayment));
		}
	}
}
