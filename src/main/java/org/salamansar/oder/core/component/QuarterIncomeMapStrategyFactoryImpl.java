package org.salamansar.oder.core.component;

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
	public QuarterIncomeMapStrategy getStrategy(Quarter quarter, boolean byQuants) {
		if (quarter == Quarter.YEAR && !byQuants) {
			return summarizedStrategy;
		} else {
			return quantizedStrategy;
		}
	}
	
}
