package org.salamansar.oder.core.service;

import java.util.List;
import org.salamansar.oder.core.domain.FixedPayment;
import org.salamansar.oder.core.domain.Income;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Tax;

/**
 *
 * @author Salamansar
 */
public interface TaxCalculator {
	
	List<Tax> calculateIncomeTaxes(List<Income> incomes);
	
	List<Tax> calculateFixedPayments(PaymentPeriod period);
	
	List<Tax> calculateOnePercent(List<Income> incomes);
}
