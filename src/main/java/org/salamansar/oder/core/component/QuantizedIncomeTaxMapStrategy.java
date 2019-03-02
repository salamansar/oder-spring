package org.salamansar.oder.core.component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.salamansar.oder.core.domain.Income;
import org.salamansar.oder.core.domain.Tax;
import org.salamansar.oder.core.domain.TaxCategory;
import org.salamansar.oder.core.utils.PaymentsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Salamansar
 */
@Component("strategy.map.incomeTax.quantized")
public class QuantizedIncomeTaxMapStrategy implements IncomeTaxMapStrategy { //todo: refactor for using quarter incomes
	private static final BigDecimal TAX_RATE = BigDecimal.valueOf(0.06); //todo: must be replaced for calculation, since rate may be different
	@Autowired
	private PaymentPeriodCalculator periodCalcualtor;

	@Override
	public List<Tax> map(List<Income> incomes) {
		return incomes.stream()
				.collect(Collectors.groupingBy(income -> periodCalcualtor.calculatePeriod(income.getIncomeDate())))
				.entrySet().stream()
				.map(e -> {
					BigDecimal sum = PaymentsUtils.incomesSum(e.getValue()).multiply(TAX_RATE);
					Tax tax = new Tax(TaxCategory.INCOME_TAX);
					tax.setPayment(sum);
					tax.setPeriod(e.getKey());
					return tax;
				})
				.collect(Collectors.toList());
	}
}
