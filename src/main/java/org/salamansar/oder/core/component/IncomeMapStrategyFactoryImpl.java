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
public class IncomeMapStrategyFactoryImpl implements IncomeMapStrategyFactory {
	@Autowired
	@Qualifier("summarizedIncomeMapStrategy")
	private IncomeMapStrategy summarizedStrategy;
	@Autowired
	@Qualifier("quantizedIncomeMapStrategy")
	private IncomeMapStrategy quantizedStrategy;

	@Override
	public IncomeMapStrategy getStrategy(PaymentPeriod period, TaxCalculationSettings settings) {
		if(period.getQuarter() == Quarter.YEAR && !settings.getByQuants()) {
			return summarizedStrategy;
		} else {
			return quantizedStrategy;
		}
	}
	
}
