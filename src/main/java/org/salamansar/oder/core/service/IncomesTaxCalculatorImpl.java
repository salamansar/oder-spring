package org.salamansar.oder.core.service;

import java.util.List;
import org.salamansar.oder.core.component.IncomeMapStrategy;
import org.salamansar.oder.core.component.IncomeMapStrategyFactory;
import org.salamansar.oder.core.domain.Income;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Tax;
import org.salamansar.oder.core.domain.TaxCalculationSettings;
import org.salamansar.oder.core.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Salamansar
 */
@Service
public class IncomesTaxCalculatorImpl implements IncomeTaxCalculator {
	@Autowired
	private IncomeService incomesService;
	@Autowired
	private IncomeMapStrategyFactory strategyFactory;

	@Override
	public List<Tax> calculateIncomeTaxes(User user, PaymentPeriod period, TaxCalculationSettings settings) {
		List<Income> incomes = incomesService.findIncomes(user, period);
		IncomeMapStrategy strategy = strategyFactory.getStrategy(period, settings);
		return strategy.map(incomes);
	}
	
}
