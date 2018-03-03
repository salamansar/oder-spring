package org.salamansar.oder.core.dao;

import java.util.List;
import org.salamansar.oder.core.domain.Income;
import org.salamansar.oder.core.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Salamansar
 */
public interface IncomeDao extends CrudRepository<Income, Long> {
    List<Income> findIncomeByUserOrderByIncomeDateDesc(User user);
}
