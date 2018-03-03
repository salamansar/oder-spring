package org.salamansar.oder.core.service;

import java.util.List;
import org.salamansar.oder.core.dao.IncomeDao;
import org.salamansar.oder.core.domain.Income;
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
    
}
