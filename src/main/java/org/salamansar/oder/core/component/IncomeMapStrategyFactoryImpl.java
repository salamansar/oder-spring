package org.salamansar.oder.core.component;

import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
import org.salamansar.oder.core.domain.TaxCalculationSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 * @author Salamansar
 */
@Component
public class IncomeMapStrategyFactoryImpl implements IncomeTaxMapStrategyFactory {
	@Autowired
	@Qualifier("strategy.map.incomeTax.summarized")
	private IncomeTaxMapStrategy summarizedStrategy;
	@Autowired
	@Qualifier("strategy.map.incomeTax.quantized")
	private IncomeTaxMapStrategy quantizedStrategy;

	@Override
	public IncomeTaxMapStrategy getStrategy(PaymentPeriod period, TaxCalculationSettings settings) {
		if(period.getQuarter() == Quarter.YEAR && !settings.getByQuants()) {
			return summarizedStrategy;
		} else {
			return quantizedStrategy;
		}
	}
	
}
