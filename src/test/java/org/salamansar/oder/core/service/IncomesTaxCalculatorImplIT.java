package org.salamansar.oder.core.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;
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
public class IncomesTaxCalculatorImplIT extends AbstractCoreIntegrationTest {
	@Autowired
	private DbEnvironmentBuilder envBuilder;
	@Autowired
	private IncomeTaxCalculator incomesTaxCalculator;
	private User user;
	
	@Before
	public void setUp() {
		envBuilder.newBuild().createObject(User.class).asParent().alias("user")
				.createObject(Income.class, LocalDate.of(2018, Month.FEBRUARY, 1), BigDecimal.valueOf(150000))
				.createObject(Income.class, LocalDate.of(2018, Month.MARCH, 31), BigDecimal.valueOf(6000.5))
				.createObject(Income.class, LocalDate.of(2018, Month.SEPTEMBER, 10), BigDecimal.valueOf(100.3));
		user = envBuilder.getEnvironment().getByAlias("user");
	}
	
	@Test
	public void calculateQuantized() {
		List<Tax> result = incomesTaxCalculator.calculateIncomeTaxes(
				user,
				new PaymentPeriod(2018, Quarter.YEAR), 
				new TaxCalculationSettings().splitByQuants(true));
		
		assertNotNull(result);
		assertEquals(2, result.size());
		Optional<Tax> tax1 = result.stream()
				.filter(t -> new PaymentPeriod(2018, Quarter.I).equals(t.getPeriod()))
				.findAny();
		assertTrue(tax1.isPresent());
		assertEquals(TaxCategory.INCOME_TAX, tax1.get().getCatgory());
		assertTrue(BigDecimal.valueOf(9360.03).compareTo(tax1.get().getPayment()) == 0);
		Optional<Tax> tax2 = result.stream()
				.filter(t -> new PaymentPeriod(2018, Quarter.III).equals(t.getPeriod()))
				.findAny();
		assertTrue(tax2.isPresent());
		assertEquals(TaxCategory.INCOME_TAX, tax2.get().getCatgory());
		assertTrue(BigDecimal.valueOf(6.018).compareTo(tax2.get().getPayment()) == 0);
	}
	
	@Test
	public void calculateQuantizedWithRoundUp() {
		List<Tax> result = incomesTaxCalculator.calculateIncomeTaxes(
				user,
				new PaymentPeriod(2018, Quarter.YEAR), 
				new TaxCalculationSettings().splitByQuants(true).withRoundUp(true));
		
		assertNotNull(result);
		assertEquals(2, result.size());
		Optional<Tax> tax1 = result.stream()
				.filter(t -> new PaymentPeriod(2018, Quarter.I).equals(t.getPeriod()))
				.findAny();
		assertTrue(tax1.isPresent());
		assertEquals(TaxCategory.INCOME_TAX, tax1.get().getCatgory());
		assertTrue(BigDecimal.valueOf(9361).compareTo(tax1.get().getPayment()) == 0);
		Optional<Tax> tax2 = result.stream()
				.filter(t -> new PaymentPeriod(2018, Quarter.III).equals(t.getPeriod()))
				.findAny();
		assertTrue(tax2.isPresent());
		assertEquals(TaxCategory.INCOME_TAX, tax2.get().getCatgory());
		assertTrue(BigDecimal.valueOf(7).compareTo(tax2.get().getPayment()) == 0);
	}
	
	@Test
	public void calculateSummarized() {
		List<Tax> result = incomesTaxCalculator.calculateIncomeTaxes(
				user,
				new PaymentPeriod(2018, Quarter.YEAR), 
				new TaxCalculationSettings());
		
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(TaxCategory.INCOME_TAX, result.get(0).getCatgory());
		assertEquals(new PaymentPeriod(2018, Quarter.YEAR), result.get(0).getPeriod());
		assertTrue(BigDecimal.valueOf(9366.048).compareTo(result.get(0).getPayment()) == 0);
	}
	
	@Test
	public void calculateSummarizedWithRoundUp() {
		List<Tax> result = incomesTaxCalculator.calculateIncomeTaxes(
				user,
				new PaymentPeriod(2018, Quarter.YEAR),
				new TaxCalculationSettings().withRoundUp(true));

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(TaxCategory.INCOME_TAX, result.get(0).getCatgory());
		assertEquals(new PaymentPeriod(2018, Quarter.YEAR), result.get(0).getPeriod());
		assertTrue(BigDecimal.valueOf(9367).compareTo(result.get(0).getPayment()) == 0);
	}
	
}
