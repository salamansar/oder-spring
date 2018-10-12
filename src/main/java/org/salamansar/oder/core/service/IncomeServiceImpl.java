package org.salamansar.oder.core.service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import org.salamansar.oder.core.dao.IncomeDao;
import org.salamansar.oder.core.domain.Income;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Salamansar
 */
@Service
public class IncomeServiceImpl implements IncomeService {

	@Autowired
	private IncomeDao incomeDao;

	@Override
	@Transactional
	public List<Income> getAllIncomes(User user) {
		return incomeDao.findIncomeByUserOrderByIncomeDateDesc(user);
	}

	@Override
	@Transactional
	public Long addIncome(Income income) {
		income.setId(null);
		Income savedIncome = incomeDao.save(income);
		return savedIncome.getId();
	}

	@Override
	public List<Income> findIncomes(User user, PaymentPeriod period) {
		LocalDate dateFrom = LocalDate.of(period.getYear(), period.getStartMonth(), 1);
		Month monthTo = period.getEndMonth();
		LocalDate dateTo = LocalDate.of(period.getYear(), monthTo, monthTo.maxLength());
		return incomeDao.findIncomeByUserAndIncomeDateBetween(user, dateFrom, dateTo);
	}

}
