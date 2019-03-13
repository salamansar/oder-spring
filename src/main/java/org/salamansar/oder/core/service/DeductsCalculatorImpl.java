package org.salamansar.oder.core.service;

import java.util.List;
import org.salamansar.oder.core.component.DeductCalculatingStrategy;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
import org.salamansar.oder.core.domain.TaxCalculationSettings;
import org.salamansar.oder.core.domain.TaxDeduction;
import org.salamansar.oder.core.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author Salamansar
 */
@Service
public class DeductsCalculatorImpl implements DeductsCalculator {
	@Autowired
	@Qualifier("strategy.calculate.deduct.single")
	private DeductCalculatingStrategy singleMonthStrategy;
	@Autowired
	@Qualifier("strategy.calculate.deduct.allYear")
	private DeductCalculatingStrategy allYearStrategy;
	@Autowired
	@Qualifier("strategy.calculate.deduct.quantized")
	private DeductCalculatingStrategy quantizedStrategy;
	
	@Override
	public List<TaxDeduction> calculateDeducts(User user, PaymentPeriod period, TaxCalculationSettings settings) {
		if(period.getQuarter() == Quarter.YEAR) {
			if(settings.getByQuants()) {
				return quantizedStrategy.calculateDeductions(user, period);
			} else {
				return allYearStrategy.calculateDeductions(user, period);
			}
		} else {
			return singleMonthStrategy.calculateDeductions(user, period);
		}
	}

}
