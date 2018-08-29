package org.salamansar.oder.core.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.envbuild.generator.RandomGenerator;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.salamansar.oder.core.domain.Income;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
import org.salamansar.oder.core.domain.Tax;
import org.salamansar.oder.core.domain.User;

/**
 *
 * @author Salamansar
 */
@RunWith(MockitoJUnitRunner.class)
public class TaxServiceImplTest {
	@Mock
	private IncomeService incomeService;
	@Mock
	private TaxCalculator taxCalculator;
	@InjectMocks
	private TaxServiceImpl taxService = new TaxServiceImpl();
	
	private RandomGenerator generator = new RandomGenerator();
	
	private Income income;
	private User user;
	
	@Before
	public void setUp() {
		income = generator.generate(Income.class);
		user = generator.generate(User.class);
	}
	

	@Test
	public void calculateTaxes() {
		when(incomeService.findIncomes(same(user), any(PaymentPeriod.class)))
				.thenReturn(Arrays.asList(income));
		Tax tax1 = generator.generate(Tax.class);
		Tax tax2 = generator.generate(Tax.class);
		Tax tax3 = generator.generate(Tax.class);
		when(taxCalculator.calculateFixedPayments(any(PaymentPeriod.class)))
				.thenReturn(Collections.emptyList());
		when(taxCalculator.calculateIncomeTaxes(any(List.class)))
				.thenReturn(Arrays.asList(tax1));
		when(taxCalculator.calculateOnePercent(any(List.class)))
				.thenReturn(Arrays.asList(tax2, tax3));
		
		List<Tax> result = taxService.calculateTaxes(user, new PaymentPeriod(2018, Quarter.YEAR));
		
		assertNotNull(result);
		assertEquals(3, result.size());
		assertTrue(result.contains(tax1));
		assertTrue(result.contains(tax2));
		assertTrue(result.contains(tax3));
	}
	
}