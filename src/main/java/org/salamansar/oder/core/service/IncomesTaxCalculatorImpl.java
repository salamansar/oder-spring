package org.salamansar.oder.core.service;

import java.util.List;
import java.util.stream.Collectors;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Tax;
import org.salamansar.oder.core.domain.TaxCalculationSettings;
import org.salamansar.oder.core.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.salamansar.oder.core.component.TaxAmountCalculator;
import org.salamansar.oder.core.domain.QuarterIncome;
import org.salamansar.oder.core.domain.TaxCategory;

/**
 *
 * @author Salamansar
 */
@Service
public class IncomesTaxCalculatorImpl implements IncomeTaxCalculator {
	@Autowired
	private IncomeService incomesService;
	@Autowired
	private TaxAmountCalculator amountCalculator;

	@Override
	public List<Tax> calculateIncomeTaxes(User user, PaymentPeriod period, TaxCalculationSettings settings) {
		List<QuarterIncome> incomes = incomesService.findQuarterIncomes(user, period, settings.getByQuants());
		return incomes.stream()
				.map(e -> {
					Tax tax = new Tax(TaxCategory.INCOME_TAX);
					tax.setPayment(amountCalculator.calculateTax(e));
					tax.setPeriod(e.getPeriod());
					return tax;
				})
				.collect(Collectors.toList());
	}
	
}
