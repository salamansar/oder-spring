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
public class DeductCombineStrategyFactoryImpl implements DeductCombineStrategyFactory {
	@Autowired
	@Qualifier("strategy.calculate.deductCombine.plain")
	private DeductCombineStrategy plainStrategy;
	@Autowired
	@Qualifier("strategy.calculate.deductCombine.rounding")
	private DeductCombineStrategy roundingStrategy;

	@Override
	public DeductCombineStrategy getStrategy(TaxCalculationSettings settings) {
		return settings.getRoundUp() 
				? roundingStrategy 
				: plainStrategy;
 	}

}
