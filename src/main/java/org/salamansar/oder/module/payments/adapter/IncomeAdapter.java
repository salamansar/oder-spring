package org.salamansar.oder.module.payments.adapter;

import java.util.List;
import org.salamansar.oder.core.domain.User;
import org.salamansar.oder.module.payments.dto.IncomeDto;

/**
 *
 * @author Salamansar
 */
public interface IncomeAdapter {
	List<IncomeDto> getAllIncomes(User user);
}
