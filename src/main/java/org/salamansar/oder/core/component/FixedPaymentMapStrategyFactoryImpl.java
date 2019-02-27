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
				return initialized(quantizedYearStrategy, period);
			} else {
				return initialized(allYearsStrategy, period);
			}
		} else {
			return initialized(singleMonthStrategy, period);
		}
	}
    
    private FixedPaymentMapStrategy initialized(ObjectFactory<FixedPaymentMapStrategy> factory, PaymentPeriod period) {
        FixedPaymentMapStrategy strategy = factory.getObject();
        if(strategy instanceof PeriodInitialized) {
            ((PeriodInitialized)strategy).init(period);
        }
        return strategy;
    }
	
}
