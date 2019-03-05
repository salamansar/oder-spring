package org.salamansar.oder.core.service;

import java.math.BigDecimal;
import java.util.List;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Tax;
import org.salamansar.oder.core.domain.TaxCalculationSettings;
import org.salamansar.oder.core.domain.User;

/**
 *
 * @author Salamansar
 */
public interface OnePercentTaxCalculator {
	
	List<Tax> calculateOnePercentTaxes(User user, PaymentPeriod period, TaxCalculationSettings settings);
	
	/**
	 * Calculate amount of One Percent pension tax. <br>
	 * If taxes weren't found, then zero will be returned.
	 * @return taxes sum or 0
	 */
	BigDecimal calculateOnePercentAmount(User user, PaymentPeriod period, TaxCalculationSettings settings);
}
