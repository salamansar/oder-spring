package org.salamansar.oder.core.service;

import java.util.Arrays;
import java.util.List;
import org.envbuild.generator.RandomGenerator;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.salamansar.oder.core.dao.FixedPaymentDao;
import org.salamansar.oder.core.domain.FixedPayment;

/**
 *
 * @author Salamansar
 */
@RunWith(MockitoJUnitRunner.class)
public class FixedPaymentServiceImplTest {
	@Mock
	private FixedPaymentDao fixedPaymentDao;
	@InjectMocks
	private FixedPaymentServiceImpl service;
	private RandomGenerator generator = new RandomGenerator();

	@Test
	public void findFixedPaymentsByYear() {
		Integer year = 2018;
		FixedPayment payment = generator.generate(FixedPayment.class);
		when(fixedPaymentDao.findByYear(eq(year))).thenReturn(Arrays.asList(payment));
		
		List<FixedPayment> result = service.findFixedPaymentsByYear(year);
		
		assertNotNull(result);
		assertEquals(1, result.size());
		assertSame(payment, result.get(0));
	}
	
}
