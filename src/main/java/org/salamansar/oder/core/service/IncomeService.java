package org.salamansar.oder.core.service;

import java.util.List;
import org.salamansar.oder.core.domain.Income;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.QuarterIncome;
import org.salamansar.oder.core.domain.User;

/**
 *
 * @author Salamansar
 */
public interface IncomeService {
    List<Income> getAllIncomes(User user);
    
	Income getIncome(Long id);
	
    Long addIncome(Income income);
	
	List<Income> findIncomes(User user, PaymentPeriod period);
	
	List<QuarterIncome> findQuarterIncomes(User user, PaymentPeriod period, boolean byQuants);
	
	QuarterIncome findSingleIncome(User user, PaymentPeriod period);
	
	List<Integer> findYearsWithIncomes(User user);
}
