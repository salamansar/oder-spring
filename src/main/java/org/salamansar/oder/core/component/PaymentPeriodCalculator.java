package org.salamansar.oder.core.component;

import java.util.Date;
import org.salamansar.oder.core.domain.PaymentPeriod;

/**
 *
 * @author Salamansar
 */
//todo: implement
public interface PaymentPeriodCalculator {
	
	PaymentPeriod calculatePeriod(Date date);
	
}
