package org.salamansar.oder.core.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;
import org.salamansar.oder.core.domain.Income;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Tax;
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
	private IncomeService incomeService;
	@Autowired
	private TaxCalculator taxCalculator;
	

	@Override
	public List<Tax> calculateTaxes(User user, PaymentPeriod period) {
		List<Income> incomes = incomeService.findIncomes(user, period);
		List<Tax> incomeTaxes = taxCalculator.calculateIncomeTaxes(incomes);
		List<Tax> fixedPayments = taxCalculator.calculateFixedPayments(period);
		List<Tax> onePersentPayments = taxCalculator.calculateOnePercent(incomes);
		return ListBuilder.of(incomeTaxes)
				.and(fixedPayments)
				.and(onePersentPayments)
				.build();
	}

}
