package org.salamansar.oder.core.service;

import java.util.List;
import org.salamansar.oder.core.domain.Income;
import org.salamansar.oder.core.domain.User;

/**
 *
 * @author Salamansar
 */
public interface IncomeService {
    List<Income> getAllIncomes(User user);
}
