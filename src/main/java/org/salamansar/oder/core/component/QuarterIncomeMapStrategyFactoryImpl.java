package org.salamansar.oder.core.component;

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
	public QuarterIncomeMapStrategy getStrategy(boolean byQuants) {
		if(byQuants) {
			return quantizedStrategy;
		} else {
			return summarizedStrategy;
		}
	}
	
}
