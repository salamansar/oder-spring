package org.salamansar.oder.core.service;

import java.util.List;
import org.envbuild.generator.GenerationService;
import org.junit.Test;
import static org.junit.Assert.*;
import org.salamansar.oder.core.AbstractCoreIntegrationTest;
import org.salamansar.oder.core.domain.FixedPayment;
import org.salamansar.oder.core.domain.TaxCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Salamansar
 */
@Transactional
public class FixedPaymentServiceImplIT extends AbstractCoreIntegrationTest {
	@Autowired
	private FixedPaymentService service;
	@Autowired
	private GenerationService generator;

	@Test
	public void findByYear() {
		FixedPayment payment1 = generator.generate(FixedPayment.class, TaxCategory.HEALTH_INSURANCE);
		payment1.setYear(2016);
		payment1.setId(null);
		FixedPayment payment2 = generator.generate(FixedPayment.class, TaxCategory.HEALTH_INSURANCE);
		payment2.setYear(2015);
		payment2.setId(null);
		FixedPayment payment3 = generator.generate(FixedPayment.class, TaxCategory.PENSION_INSURANCE);
		payment3.setYear(2015);
		payment3.setId(null);
		entityManager.persist(payment1);
		entityManager.persist(payment2);
		entityManager.persist(payment3);
		
		List<FixedPayment> payments = service.findFixedPaymentsByYear(2016);
		
		assertNotNull(payments);
		assertEquals(1, payments.size());
		assertEquals(payment1.getId(), payments.get(0).getId());
		assertNotNull(payments.get(0).getCategory());
		assertNotNull(payments.get(0).getValue());
		assertNotNull(payments.get(0).getYear());
		
		payments = service.findFixedPaymentsByYear(2015);
		
		assertNotNull(payments);
		assertEquals(2, payments.size());
		assertTrue(payments.stream().anyMatch(fp -> payment2.getId().equals(fp.getId())));
		assertTrue(payments.stream().anyMatch(fp -> payment3.getId().equals(fp.getId())));
		
		
	}
	
}
