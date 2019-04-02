package org.salamansar.oder.module.payments.adapter;

import java.util.List;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.User;
import org.salamansar.oder.module.payments.dto.TaxRowDto;

/**
 *
 * @author Salamansar
 */
public interface TaxAdapter {
	
	List<TaxRowDto> findAllTaxesForYear(User user, Integer year, boolean roundUp);
	
	List<TaxRowDto> findTaxesForYear(User user, Integer year, boolean roundUp);
	
	TaxRowDto findTaxForPeriod(User user, PaymentPeriod period, boolean roundUp);
	
	List<Integer> findYearsWithIncomes(User user);
}
