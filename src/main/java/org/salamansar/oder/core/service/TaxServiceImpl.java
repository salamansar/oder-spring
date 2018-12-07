package org.salamansar.oder.core.service;

import java.util.List;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Tax;
import org.salamansar.oder.core.domain.TaxCalculationSettings;
import org.salamansar.oder.core.domain.User;
import org.salamansar.oder.core.utils.ListBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Salamansar
 */
@Service
public class TaxServiceImpl implements TaxService {
	@Autowired
	private OnePercentTaxCalculator onePercentCalculator;
	@Autowired
	private FixedPaymentTaxCalculator fixedPaymentCalculator;
	@Autowired
	private IncomeTaxCalculator incomesTaxCalculator;

	@Override
	public List<Tax> calculateTaxes(User user, PaymentPeriod period) {
		return calculateTaxes(user, period, new TaxCalculationSettings());
	}

	@Override
	public List<Tax> calculateTaxes(User user, PaymentPeriod period, TaxCalculationSettings settings) {
		List<Tax> incomeTaxes = incomesTaxCalculator.calculateIncomeTaxes(user, period, settings);
		List<Tax> fixedPayments = fixedPaymentCalculator.calculateFixedPayments(period, settings);
		List<Tax> onePersentPayments = onePercentCalculator.calculateOneTaxesPercent(user, period, settings);
		return ListBuilder.of(incomeTaxes)
				.and(fixedPayments)
				.and(onePersentPayments)
				.build();
	}

}
