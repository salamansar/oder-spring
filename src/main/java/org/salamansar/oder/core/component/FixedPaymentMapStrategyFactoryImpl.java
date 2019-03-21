package org.salamansar.oder.core.component;

import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
import org.salamansar.oder.core.domain.TaxCalculationSettings;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 * @author Salamansar
 */
@Component
public class FixedPaymentMapStrategyFactoryImpl implements FixedPaymentMapStrategyFactory {
	
	@Autowired
	@Qualifier("strategy.map.fixedPayment.allYear")
	private ObjectFactory<FixedPaymentMapStrategy> allYearsStrategy;
	@Autowired
	@Qualifier("strategy.map.fixedPayment.single")
	private ObjectFactory<FixedPaymentMapStrategy> singleMonthStrategy;
	@Autowired
	@Qualifier("strategy.map.fixedPayment.quantized")
	private ObjectFactory<FixedPaymentMapStrategy> quantizedYearStrategy;
	
	@Override
	public FixedPaymentMapStrategy getStrategy(PaymentPeriod period, TaxCalculationSettings settings) {
		if(period.getQuarter() == Quarter.YEAR) {
			if(settings.getByQuants()) {
				return initialized(quantizedYearStrategy, period, settings);
			} else {
				return initialized(allYearsStrategy, period, settings);
			}
		} else {
			return initialized(singleMonthStrategy, period, settings);
		}
	}
    
    private FixedPaymentMapStrategy initialized(ObjectFactory<FixedPaymentMapStrategy> factory, PaymentPeriod period, TaxCalculationSettings settings) {
        FixedPaymentMapStrategy strategy = factory.getObject();
		FixedPaymentAmountCalculator amountCalculator = getAmountCalculationStrategy(period, settings);
		strategy.initialize(period, amountCalculator);
        return strategy;
    }

	private FixedPaymentAmountCalculator getAmountCalculationStrategy(PaymentPeriod period, TaxCalculationSettings settings) {
		FixedPaymentAmountCalculator calculator;
		if(period.getQuarter() == Quarter.YEAR && !settings.getByQuants()) {
			calculator = new PlainFixedPaymentAmountCalculator();
		} else {
			calculator = new QuarterFixedPaymentAmountCalculator();
		}
		if(settings.getRoundUp()) {
			return new RoundingFixedPaymentWrapper(calculator);
		} else {
			return calculator;
		}
	}
	
}
