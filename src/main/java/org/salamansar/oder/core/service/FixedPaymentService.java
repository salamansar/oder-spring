package org.salamansar.oder.core.service;

import java.util.List;
import org.salamansar.oder.core.domain.FixedPayment;

/**
 *
 * @author Salamansar
 */
public interface FixedPaymentService {
	List<FixedPayment> findFixedPaymentsByYear(Integer year);
}
