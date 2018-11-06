package org.salamansar.oder.core.service;

import java.util.List;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Tax;
import org.salamansar.oder.core.domain.TaxCalculationSettings;

/**
 *
 * @author Salamansar
 */
public interface FixedPaymentTaxCalculator {
	
	List<Tax> calculateFixedPayments(PaymentPeriod period, TaxCalculationSettings settings);
	
}
