package org.salamansar.oder.core.service;

import java.util.List;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Tax;
import org.salamansar.oder.core.domain.TaxCalculationSettings;
import org.salamansar.oder.core.domain.User;

/**
 *
 * @author Salamansar
 */
public interface IncomeTaxCalculator {
	
	List<Tax> calculateIncomeTaxes(User user, PaymentPeriod period, TaxCalculationSettings settings);

}
