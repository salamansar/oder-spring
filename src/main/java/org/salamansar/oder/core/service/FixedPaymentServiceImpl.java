package org.salamansar.oder.core.service;

import java.util.List;
import org.salamansar.oder.core.domain.FixedPayment;
import org.springframework.stereotype.Service;

/**
 * @author Salamansar
 */
@Service
public class FixedPaymentServiceImpl implements FixedPaymentService {

	@Override
	public List<FixedPayment> findFixedPaymentsByYear(Integer year) {
		throw new UnsupportedOperationException("Not supported yet."); //todo:implement
	}

}
