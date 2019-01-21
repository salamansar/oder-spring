package org.salamansar.oder.core.component;

import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author Salamansar
 */
@Service
public class QuarterIncomeMapStrategyFactoryImpl implements QuarterIncomeMapStartegyFactory {
	@Autowired
	@Qualifier("strategy.map.quarterIncome.quantized")
	private QuarterIncomeMapStrategy quantizedStrategy;
	@Autowired
	@Qualifier("strategy.map.quarterIncome.summarized")
	private QuarterIncomeMapStrategy summarizedStrategy;
	
	@Override
	public QuarterIncomeMapStrategy getStrategy(PaymentPeriod period, boolean byQuants) {
		if(period.getQuarter() == Quarter.YEAR && !byQuants) {
			return summarizedStrategy;
		} else {
			return quantizedStrategy;
		}
	}
	
}
