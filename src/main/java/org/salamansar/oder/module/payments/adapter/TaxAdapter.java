package org.salamansar.oder.module.payments.adapter;

import java.util.List;
import org.salamansar.oder.core.domain.User;
import org.salamansar.oder.module.payments.dto.TaxRowDto;

/**
 *
 * @author Salamansar
 */
public interface TaxAdapter {
	
	List<TaxRowDto> findAllTaxesForYear(User user, Integer year, boolean roundUp);
	
	List<TaxRowDto> findTaxesForYear(User user, Integer year, boolean roundUp);
	
	TaxRowDto findSummarizedTaxesForYear(User user, Integer year, boolean roundUp);
	
	List<Integer> findYearsWithIncomes(User user);
}
