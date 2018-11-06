package org.salamansar.oder.core.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.salamansar.oder.core.component.FixedPaymentMapStrategy;
import org.salamansar.oder.core.component.FixedPaymentMapStrategyFactory;
import org.salamansar.oder.core.domain.FixedPayment;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Tax;
import org.salamansar.oder.core.domain.TaxCalculationSettings;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Salamansar
 */
public class FixedPaymentTaxCalculatorImpl implements FixedPaymentTaxCalculator {
	@Autowired
	private FixedPaymentService fixedPaymentService;
	@Autowired
	private FixedPaymentMapStrategyFactory fixedPaymentMapFactory;

	@Override
	public List<Tax> calculateFixedPayments(PaymentPeriod period, TaxCalculationSettings settings) {
		List<FixedPayment> payments = fixedPaymentService.findFixedPaymentsByYear(period.getYear());
		FixedPaymentMapStrategy strategy = fixedPaymentMapFactory.getStrategy(period, settings);
		return payments.stream()
				.map(strategy::map)
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
	}
	
}
