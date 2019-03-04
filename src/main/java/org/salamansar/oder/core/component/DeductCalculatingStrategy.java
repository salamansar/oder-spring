package org.salamansar.oder.core.component;

import java.util.List;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.TaxCalculationSettings;
import org.salamansar.oder.core.domain.TaxDeduction;
import org.salamansar.oder.core.domain.User;

/**
 *
 * @author Salamansar
 */
public interface DeductCalculatingStrategy {
	
	List<TaxDeduction> calculateDeductions(User user, PaymentPeriod period);
}
