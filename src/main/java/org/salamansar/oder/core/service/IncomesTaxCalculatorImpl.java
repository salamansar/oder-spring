package org.salamansar.oder.core.service;

import java.util.List;
import org.salamansar.oder.core.domain.Income;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Tax;
import org.salamansar.oder.core.domain.TaxCalculationSettings;
import org.salamansar.oder.core.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.salamansar.oder.core.component.IncomeTaxMapStrategy;
import org.salamansar.oder.core.component.IncomeTaxMapStrategyFactory;

/**
 *
 * @author Salamansar
 */
@Service
public class IncomesTaxCalculatorImpl implements IncomeTaxCalculator {
	@Autowired
	private IncomeService incomesService;
	@Autowired
	private IncomeTaxMapStrategyFactory strategyFactory;

	@Override
	public List<Tax> calculateIncomeTaxes(User user, PaymentPeriod period, TaxCalculationSettings settings) {
		List<Income> incomes = incomesService.findIncomes(user, period);
		IncomeTaxMapStrategy strategy = strategyFactory.getStrategy(period, settings);
		return strategy.map(incomes);
	}
	
}
