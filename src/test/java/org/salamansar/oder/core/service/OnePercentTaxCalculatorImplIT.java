package org.salamansar.oder.core.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import org.envbuild.environment.DbEnvironmentBuilder;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.salamansar.oder.core.AbstractCoreIntegrationTest;
import org.salamansar.oder.core.domain.Income;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
import org.salamansar.oder.core.domain.Tax;
import org.salamansar.oder.core.domain.TaxCalculationSettings;
import org.salamansar.oder.core.domain.TaxCategory;
import org.salamansar.oder.core.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Salamansar
 */
@Transactional
public class OnePercentTaxCalculatorImplIT extends AbstractCoreIntegrationTest {
	@Autowired
	private DbEnvironmentBuilder envBuilder;
	@Autowired
	private OnePercentTaxCalculator taxCalcualtor;
	private User user;
	
	@Before
	public void setUp() {
		envBuilder.newBuild().createObject(User.class).asParent().alias("user")
				.createObject(Income.class, LocalDate.of(2018, Month.FEBRUARY, 1), BigDecimal.valueOf(150000))
				.createObject(Income.class, LocalDate.of(2018, Month.MARCH, 31), BigDecimal.valueOf(210000.5))
				.createObject(Income.class, LocalDate.of(2019, Month.SEPTEMBER, 10), BigDecimal.valueOf(320000.3));
		user = envBuilder.getEnvironment().getByAlias("user");
	}
	
	@Test
	public void calculateQuantized() {
		List<Tax> result = taxCalcualtor.calculateOnePercentTaxes(
				user,
				new PaymentPeriod(2019, Quarter.YEAR), 
				new TaxCalculationSettings().splitByQuants(true));
		
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(new PaymentPeriod(2019, Quarter.I),result.get(0).getPeriod());
		assertEquals(TaxCategory.PENSION_PERCENT, result.get(0).getCatgory());
		assertTrue(BigDecimal.valueOf(600.005).compareTo(result.get(0).getPayment()) == 0);
	}
	
	@Test
	public void calculateSummarized() {
		List<Tax> result = taxCalcualtor.calculateOnePercentTaxes(
				user,
				new PaymentPeriod(2019, Quarter.YEAR), 
				new TaxCalculationSettings());
		
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(TaxCategory.PENSION_PERCENT, result.get(0).getCatgory());
		assertEquals(new PaymentPeriod(2019, Quarter.YEAR), result.get(0).getPeriod());
		assertTrue(BigDecimal.valueOf(200.003).compareTo(result.get(0).getPayment()) == 0);
	}
	
}
