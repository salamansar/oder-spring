package org.salamansar.oder.core.component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import org.salamansar.oder.core.domain.PaymentPeriod;

/**
 *
 * @author Salamansar
 */
public interface PaymentPeriodCalculator {
	
	PaymentPeriod calculatePeriod(Date date);
	
	PaymentPeriod calculatePeriod(LocalDate date);
	
	PaymentPeriod calculatePeriod(LocalDateTime date);
	
}
