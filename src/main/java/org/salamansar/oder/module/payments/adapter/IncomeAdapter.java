package org.salamansar.oder.module.payments.adapter;

import java.util.List;
import org.salamansar.oder.core.domain.User;
import org.salamansar.oder.module.payments.dto.IncomeDto;
import org.salamansar.oder.module.payments.dto.QuarterIncomeDto;

/**
 *
 * @author Salamansar
 */
public interface IncomeAdapter {
	
	List<IncomeDto> getAllIncomes(User user);
	
	List<QuarterIncomeDto> getAllYearIncomes(User user);
	
	Long addIncome(User user, IncomeDto dto);
	
	void editIncome(User user, IncomeDto dto);
	
	IncomeDto getIncome(User user, Long id);
	
	void deleteIncome(User user, Long id);
}
