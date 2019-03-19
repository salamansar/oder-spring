package org.salamansar.oder.core.component;

import org.salamansar.oder.core.domain.TaxCalculationSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 * @author Salamansar
 */
@Component
public class TaxAmountCalculatorFactoryImpl implements TaxAmountCalculatorFactory {
	@Autowired
	@Qualifier("strategy.calculate.taxAmount.simple")
	private TaxAmountCalculator simpleCalculator;
	@Autowired
	@Qualifier("strategy.calculate.taxAmount.rounded")
	private TaxAmountCalculator roundedCalculator;

	@Override
	public TaxAmountCalculator getCalculator(TaxCalculationSettings settings) {
		if(settings.getRoundUp()) {
			return roundedCalculator;
		} else {
			return simpleCalculator;
		}
	}
	
}
