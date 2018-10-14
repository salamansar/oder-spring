package org.salamansar.oder.core.dao;

import java.util.List;
import org.salamansar.oder.core.domain.FixedPayment;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Salamansar
 */
public interface FixedPaymentDao extends CrudRepository<FixedPayment, Long> {

	List<FixedPayment> findByYear(Integer year);
	
}
