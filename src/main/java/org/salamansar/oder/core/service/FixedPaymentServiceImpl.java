package org.salamansar.oder.core.service;

import java.util.List;
import org.salamansar.oder.core.dao.FixedPaymentDao;
import org.salamansar.oder.core.domain.FixedPayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Salamansar
 */
@Service
public class FixedPaymentServiceImpl implements FixedPaymentService {
	@Autowired
	private FixedPaymentDao fixedPaymentDao;

	@Override
	public List<FixedPayment> findFixedPaymentsByYear(Integer year) {
		return fixedPaymentDao.findByYear(year);
	}

}
