package org.salamansar.oder.core.dao;

import java.time.LocalDate;
import java.util.List;
import org.salamansar.oder.core.domain.Income;
import org.salamansar.oder.core.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Salamansar
 */
public interface IncomeDao extends CrudRepository<Income, Long> {

	List<Income> findIncomeByUserOrderByIncomeDateDesc(User user);

	List<Income> findIncomeByUserAndIncomeDateBetween(User user, LocalDate dateFrom, LocalDate dateTo);
	
	@Query("select incomeDate from Income where user = ?1")
	List<LocalDate> findAllIncomesDates(User user);
}
