package org.salamansar.oder.core.service;

import java.util.List;
import java.util.Optional;
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
    
	Optional<Income> getIncome(Long id);
	
    Long addIncome(Income income);
	
	void updateIncome(Income income);
	
	void deleteIncome(Long id);
	
	void deleteIncome(Income income);
	
	List<Income> findIncomes(User user, PaymentPeriod period);
	
	List<QuarterIncome> findQuarterIncomes(User user, PaymentPeriod period, boolean byQuants);
	
	QuarterIncome findSingleIncome(User user, PaymentPeriod period);
	
	List<Integer> findYearsWithIncomes(User user);
}
