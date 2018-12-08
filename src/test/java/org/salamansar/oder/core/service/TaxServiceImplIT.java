package org.salamansar.oder.core.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import org.envbuild.generator.RandomGenerator;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.salamansar.oder.core.AbstractCoreIntegrationTest;
import org.salamansar.oder.core.domain.FixedPayment;
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
public class TaxServiceImplIT extends AbstractCoreIntegrationTest {
	@Autowired
	private TaxService service;
	
	private RandomGenerator generator = new RandomGenerator();
	private User user;

	@Before
	public void setUp() {
		user = generator.generate(User.class);
		user.setId(null);
		entityManager.persist(user);
		prepareFixedPayments();
		prepareTestIncomes();
	}
	
	@Test
	public void calculateTaxesForFirstQuarter() {
		PaymentPeriod period = new PaymentPeriod(2016, Quarter.I);
		List<Tax> taxes = service.calculateTaxes(user, period);
		
		assertNotNull(taxes);
		assertEquals(2, taxes.size());
		assertTrue(taxes.stream().allMatch(t -> 
				t.getCatgory() != null 
				&& t.getPayment() != null 
				&& t.getPeriod() != null && t.getPeriod().equals(period)));
		assertTrue(taxes.stream().anyMatch(t -> TaxCategory.HEALTH_INSURANCE == t.getCatgory()));
		assertTrue(taxes.stream().anyMatch(t -> TaxCategory.PENSION_INSURANCE == t.getCatgory()));
		
		taxes = service.calculateTaxes(user, new PaymentPeriod(2015, Quarter.I));
		
		assertNotNull(taxes);
		assertTrue(taxes.isEmpty());
	}
	
	@Test
	public void calculateTaxesForSecondQuarter() {
		PaymentPeriod period = new PaymentPeriod(2016, Quarter.II);
		List<Tax> taxes = service.calculateTaxes(user, period);
		
		assertNotNull(taxes);
		assertEquals(3, taxes.size());
		assertTrue(taxes.stream().allMatch(t -> 
				t.getCatgory() != null 
				&& t.getPayment() != null 
				&& t.getPeriod() != null && t.getPeriod().equals(period)));
		assertTrue(taxes.stream().anyMatch(t -> TaxCategory.HEALTH_INSURANCE == t.getCatgory()));
		assertTrue(taxes.stream().anyMatch(t -> TaxCategory.PENSION_INSURANCE == t.getCatgory()));
		assertTrue(taxes.stream().anyMatch(t -> TaxCategory.INCOME_TAX == t.getCatgory()));
		
		taxes = service.calculateTaxes(user, new PaymentPeriod(2015, Quarter.II));
		
		assertNotNull(taxes);
		assertTrue(taxes.isEmpty());
	}
	
	@Test
	public void calculateTaxesForThirdQuarter() {
		PaymentPeriod period = new PaymentPeriod(2016, Quarter.III);
		List<Tax> taxes = service.calculateTaxes(user, period);
		
		assertNotNull(taxes);
		assertEquals(3, taxes.size());
		assertTrue(taxes.stream().allMatch(t -> 
				t.getCatgory() != null 
				&& t.getPayment() != null 
				&& t.getPeriod() != null && t.getPeriod().equals(period)));
		assertTrue(taxes.stream().anyMatch(t -> TaxCategory.HEALTH_INSURANCE == t.getCatgory()));
		assertTrue(taxes.stream().anyMatch(t -> TaxCategory.PENSION_INSURANCE == t.getCatgory()));
		assertTrue(taxes.stream().anyMatch(t -> TaxCategory.INCOME_TAX == t.getCatgory()));
		
		PaymentPeriod period2015 = new PaymentPeriod(2015, Quarter.III);
		taxes = service.calculateTaxes(user, period2015);
		
		assertNotNull(taxes);
		assertEquals(1, taxes.size());
		assertNotNull(taxes.get(0).getCatgory());
		assertNotNull(taxes.get(0).getPayment());
		assertNotNull(taxes.get(0).getPeriod());
		assertEquals(period2015, taxes.get(0).getPeriod());
		assertEquals(TaxCategory.INCOME_TAX, taxes.get(0).getCatgory());
	}
	
	@Test
	public void calculateTaxesForFourthQuarter() {
		PaymentPeriod period = new PaymentPeriod(2016, Quarter.IV);
		List<Tax> taxes = service.calculateTaxes(user, period);
		
		assertNotNull(taxes);
		assertEquals(2, taxes.size());
		assertTrue(taxes.stream().allMatch(t -> 
				t.getCatgory() != null 
				&& t.getPayment() != null 
				&& t.getPeriod() != null && t.getPeriod().equals(period)));
		assertTrue(taxes.stream().anyMatch(t -> TaxCategory.HEALTH_INSURANCE == t.getCatgory()));
		assertTrue(taxes.stream().anyMatch(t -> TaxCategory.PENSION_INSURANCE == t.getCatgory()));
		
		taxes = service.calculateTaxes(user, new PaymentPeriod(2015, Quarter.IV));
		
		assertNotNull(taxes);
		assertTrue(taxes.isEmpty());
	}
	
	@Test
	public void calculateTaxesForYear() {
		PaymentPeriod period2016 = new PaymentPeriod(2016, Quarter.YEAR);
		List<Tax> taxes = service.calculateTaxes(user, period2016, new TaxCalculationSettings().splitByQuants(true));

		assertNotNull(taxes);
		assertEquals(10, taxes.size());
		assertTrue(taxes.stream().allMatch(t
				-> t.getCatgory() != null
				&& t.getPayment() != null
				&& t.getPeriod()  != null && t.getPeriod().getYear().equals(2016)));
		assertTrue(taxes.stream().anyMatch(t -> TaxCategory.HEALTH_INSURANCE == t.getCatgory() && t.getPeriod().getQuarter() == Quarter.I));
		assertTrue(taxes.stream().anyMatch(t -> TaxCategory.HEALTH_INSURANCE == t.getCatgory() && t.getPeriod().getQuarter() == Quarter.II));
		assertTrue(taxes.stream().anyMatch(t -> TaxCategory.HEALTH_INSURANCE == t.getCatgory() && t.getPeriod().getQuarter() == Quarter.III));
		assertTrue(taxes.stream().anyMatch(t -> TaxCategory.HEALTH_INSURANCE == t.getCatgory() && t.getPeriod().getQuarter() == Quarter.IV));
		assertTrue(taxes.stream().anyMatch(t -> TaxCategory.PENSION_INSURANCE == t.getCatgory() && t.getPeriod().getQuarter() == Quarter.I));
		assertTrue(taxes.stream().anyMatch(t -> TaxCategory.PENSION_INSURANCE == t.getCatgory() && t.getPeriod().getQuarter() == Quarter.II));
		assertTrue(taxes.stream().anyMatch(t -> TaxCategory.PENSION_INSURANCE == t.getCatgory() && t.getPeriod().getQuarter() == Quarter.III));
		assertTrue(taxes.stream().anyMatch(t -> TaxCategory.PENSION_INSURANCE == t.getCatgory() && t.getPeriod().getQuarter() == Quarter.II));
		assertTrue(taxes.stream().anyMatch(t -> TaxCategory.INCOME_TAX == t.getCatgory() && t.getPeriod().getQuarter() == Quarter.II));
		assertTrue(taxes.stream().anyMatch(t -> TaxCategory.INCOME_TAX == t.getCatgory() && t.getPeriod().getQuarter() == Quarter.III));

		PaymentPeriod period2015 = new PaymentPeriod(2015, Quarter.YEAR);
		taxes = service.calculateTaxes(user, period2015);

		assertNotNull(taxes);
		assertEquals(1, taxes.size());
		assertNotNull(taxes.get(0).getCatgory());
		assertNotNull(taxes.get(0).getPayment());
		assertNotNull(taxes.get(0).getPeriod());
		assertEquals(period2015, taxes.get(0).getPeriod());
		assertEquals(TaxCategory.INCOME_TAX, taxes.get(0).getCatgory());
	}
	
	@Test
	public void calculateTaxesForYearSummarized() {
		PaymentPeriod period2016 = new PaymentPeriod(2016, Quarter.YEAR);
		List<Tax> taxes = service.calculateTaxes(user, period2016);

		assertNotNull(taxes);
		assertEquals(4, taxes.size());
		assertTrue(taxes.stream().allMatch(t
				-> t.getCatgory() != null
				&& t.getPayment() != null
				&& t.getPeriod()  != null && t.getPeriod().equals(period2016)));
		assertEquals(1, taxes.stream().filter(t -> TaxCategory.HEALTH_INSURANCE == t.getCatgory()).count());
		assertEquals(1, taxes.stream().filter(t -> TaxCategory.PENSION_INSURANCE == t.getCatgory()).count());
		assertEquals(1, taxes.stream().filter(t -> TaxCategory.INCOME_TAX == t.getCatgory()).count());
		assertEquals(1, taxes.stream().filter(t -> TaxCategory.PENSION_PERCENT == t.getCatgory()).count());

		PaymentPeriod period2015 = new PaymentPeriod(2015, Quarter.YEAR);
		taxes = service.calculateTaxes(user, period2015);

		assertNotNull(taxes);
		assertEquals(1, taxes.size());
		assertNotNull(taxes.get(0).getCatgory());
		assertNotNull(taxes.get(0).getPayment());
		assertNotNull(taxes.get(0).getPeriod());
		assertEquals(period2015, taxes.get(0).getPeriod());
		assertEquals(TaxCategory.INCOME_TAX, taxes.get(0).getCatgory());
	}

	private void prepareFixedPayments() {
		FixedPayment payment1 = generator.generate(FixedPayment.class, TaxCategory.HEALTH_INSURANCE);
		payment1.setId(null);
		payment1.setYear(2016);
		entityManager.persist(payment1);
		FixedPayment payment2 = generator.generate(FixedPayment.class, TaxCategory.PENSION_INSURANCE);
		payment2.setId(null);
		payment2.setYear(2016);
		entityManager.persist(payment2);
	}
	
	private void prepareTestIncomes() {
		Income income1 = generator.generate(Income.class, user, LocalDate.of(2016, Month.APRIL, 5));
		income1.setId(null);
		income1.setAmount(BigDecimal.valueOf(320000));
		entityManager.persist(income1);
		Income income2 = generator.generate(Income.class, user, LocalDate.of(2016, Month.JUNE, 30));
		income2.setId(null);
		entityManager.persist(income2);
		Income income3 = generator.generate(Income.class, user, LocalDate.of(2016, Month.JULY, 1));
		income3.setId(null);
		entityManager.persist(income3);
		Income income4 = generator.generate(Income.class, user, LocalDate.of(2015, Month.JULY, 1));
		income4.setId(null);
		entityManager.persist(income4);
	}
}
