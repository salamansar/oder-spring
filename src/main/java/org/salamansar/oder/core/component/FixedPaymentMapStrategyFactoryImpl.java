package org.salamansar.oder.core.component;

import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
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
	@Qualifier("allYearPaymentMapStrategy")
	private ObjectFactory<FixedPaymentMapStrategy> allYearsStrategy;
	@Autowired
	@Qualifier("singleMonthPaymentMapStrategy")
	private ObjectFactory<FixedPaymentMapStrategy> singleMonthStrategy;
	
	@Override
	public FixedPaymentMapStrategy getStartegy(PaymentPeriod period) {
		if(period.getQuarter() == Quarter.YEAR) {
			return initialized(allYearsStrategy, period);
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
