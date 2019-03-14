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
import org.salamansar.oder.core.domain.DeductibleTax;
import org.salamansar.oder.core.domain.FixedPayment;
import org.salamansar.oder.core.domain.Income;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
import org.salamansar.oder.core.domain.Tax;
import org.salamansar.oder.core.domain.TaxCalculationSettings;
import org.salamansar.oder.core.domain.TaxCategory;
import org.salamansar.oder.core.domain.TaxDeduction;
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
		assertTrue(taxes.stream().anyMatch(t -> TaxCategory.PENSION_INSURANCE == t.getCatgory() && t.getPeriod().getQuarter() == Quarter.IV));
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
	
	@Test
	public void calculateDeductsForYearSummarized() {
		PaymentPeriod period2016 = new PaymentPeriod(2016, Quarter.YEAR);
		List<TaxDeduction> deducts = service.calculateDeductions(user, period2016, TaxCalculationSettings.defaults());

		assertNotNull(deducts);
		assertEquals(1, deducts.size());
		assertNotNull(deducts.get(0).getPeriod());
		assertNotNull(deducts.get(0).getPeriod());
		assertEquals(period2016, deducts.get(0).getPeriod());

		PaymentPeriod period2015 = new PaymentPeriod(2015, Quarter.YEAR);
		deducts = service.calculateDeductions(user, period2015, TaxCalculationSettings.defaults());

		assertNotNull(deducts);
		assertEquals(0, deducts.size());
	}
	
	@Test
	public void calculateDeductsForYearQuantized() {
		PaymentPeriod period2016 = new PaymentPeriod(2016, Quarter.YEAR);
		List<TaxDeduction> deducts = service.calculateDeductions(user, period2016, TaxCalculationSettings.defaults().splitByQuants(true));

		assertNotNull(deducts);
		assertEquals(4, deducts.size());
		assertTrue(deducts.stream().allMatch(t
				-> t.getDeduction()!= null
				&& t.getPeriod() != null && t.getPeriod().getYear().equals(period2016.getYear())));
		assertEquals(1, deducts.stream().filter(t -> Quarter.I == t.getPeriod().getQuarter()).count());
		assertEquals(1, deducts.stream().filter(t -> Quarter.II == t.getPeriod().getQuarter()).count());
		assertEquals(1, deducts.stream().filter(t -> Quarter.III == t.getPeriod().getQuarter()).count());
		assertEquals(1, deducts.stream().filter(t -> Quarter.IV == t.getPeriod().getQuarter()).count());

		PaymentPeriod period2015 = new PaymentPeriod(2015, Quarter.YEAR);
		deducts = service.calculateDeductions(user, period2015, TaxCalculationSettings.defaults().splitByQuants(true));

		assertNotNull(deducts);
		assertEquals(0, deducts.size());
	}
	
	@Test
	public void calculateDeductsForQuarter() {
		PaymentPeriod period2016 = new PaymentPeriod(2016, Quarter.II);
		List<TaxDeduction> deducts = service.calculateDeductions(user, period2016, TaxCalculationSettings.defaults());

		assertNotNull(deducts);
		assertEquals(1, deducts.size());
		assertNotNull(deducts.get(0).getPeriod());
		assertNotNull(deducts.get(0).getPeriod());
		assertEquals(period2016, deducts.get(0).getPeriod());

		PaymentPeriod period2015 = new PaymentPeriod(2015, Quarter.II);
		deducts = service.calculateDeductions(user, period2015, TaxCalculationSettings.defaults());

		assertNotNull(deducts);
		assertEquals(0, deducts.size());
	}
	
	@Test
	public void calculateDeductiableTaxesForFirstQuarter() {
		PaymentPeriod period = new PaymentPeriod(2016, Quarter.I);
		List<DeductibleTax> taxes = service.calculateDeductedTaxes(user, period);

		assertNotNull(taxes);
		assertEquals(2, taxes.size());
		assertTrue(taxes.stream().allMatch(t
				-> t.getCatgory() != null
				&& t.getPayment() != null
				&& t.getDeduction() == null
				&& t.getPeriod() != null && t.getPeriod().equals(period)));
		assertTrue(taxes.stream().anyMatch(t -> TaxCategory.HEALTH_INSURANCE == t.getCatgory()));
		assertTrue(taxes.stream().anyMatch(t -> TaxCategory.PENSION_INSURANCE == t.getCatgory()));

		taxes = service.calculateDeductedTaxes(user, new PaymentPeriod(2015, Quarter.I));

		assertNotNull(taxes);
		assertTrue(taxes.isEmpty());
	}

	@Test
	public void calculateDeductiableTaxesForSecondQuarter() {
		PaymentPeriod period = new PaymentPeriod(2016, Quarter.II);
		List<DeductibleTax> taxes = service.calculateDeductedTaxes(user, period);

		assertNotNull(taxes);
		assertEquals(3, taxes.size());
		assertTrue(taxes.stream().allMatch(t
				-> t.getCatgory() != null
				&& t.getPayment() != null
				&& t.getPeriod() != null && t.getPeriod().equals(period)));
		assertTrue(taxes.stream().anyMatch(t -> TaxCategory.HEALTH_INSURANCE == t.getCatgory() && t.getDeduction() == null));
		assertTrue(taxes.stream().anyMatch(t -> TaxCategory.PENSION_INSURANCE == t.getCatgory() && t.getDeduction() == null));
		assertTrue(taxes.stream().anyMatch(t -> TaxCategory.INCOME_TAX == t.getCatgory() && t.getDeduction() != null));

		taxes = service.calculateDeductedTaxes(user, new PaymentPeriod(2015, Quarter.II));

		assertNotNull(taxes);
		assertTrue(taxes.isEmpty());
	}

	@Test
	public void calculateDeductiableTaxesForThirdQuarter() {
		PaymentPeriod period = new PaymentPeriod(2016, Quarter.III);
		List<DeductibleTax> taxes = service.calculateDeductedTaxes(user, period);

		assertNotNull(taxes);
		assertEquals(3, taxes.size());
		assertTrue(taxes.stream().allMatch(t
				-> t.getCatgory() != null
				&& t.getPayment() != null
				&& t.getPeriod() != null && t.getPeriod().equals(period)));
		assertTrue(taxes.stream().anyMatch(t -> TaxCategory.HEALTH_INSURANCE == t.getCatgory()));
		assertTrue(taxes.stream().anyMatch(t -> TaxCategory.PENSION_INSURANCE == t.getCatgory()));
		assertTrue(taxes.stream().anyMatch(t -> TaxCategory.INCOME_TAX == t.getCatgory()));

		PaymentPeriod period2015 = new PaymentPeriod(2015, Quarter.III);
		taxes = service.calculateDeductedTaxes(user, period2015);

		assertNotNull(taxes);
		assertEquals(1, taxes.size());
		assertNotNull(taxes.get(0).getCatgory());
		assertNotNull(taxes.get(0).getPayment());
		assertNotNull(taxes.get(0).getPeriod());
		assertEquals(period2015, taxes.get(0).getPeriod());
		assertEquals(TaxCategory.INCOME_TAX, taxes.get(0).getCatgory());
	}

	@Test
	public void calculateDeductiableTaxesForFourthQuarter() {
		PaymentPeriod period = new PaymentPeriod(2016, Quarter.IV);
		List<DeductibleTax> taxes = service.calculateDeductedTaxes(user, period);

		assertNotNull(taxes);
		assertEquals(2, taxes.size());
		assertTrue(taxes.stream().allMatch(t
				-> t.getCatgory() != null
				&& t.getPayment() != null
				&& t.getDeduction() == null
				&& t.getPeriod() != null && t.getPeriod().equals(period)));
		assertTrue(taxes.stream().anyMatch(t -> TaxCategory.HEALTH_INSURANCE == t.getCatgory()));
		assertTrue(taxes.stream().anyMatch(t -> TaxCategory.PENSION_INSURANCE == t.getCatgory()));

		taxes = service.calculateDeductedTaxes(user, new PaymentPeriod(2015, Quarter.IV));

		assertNotNull(taxes);
		assertTrue(taxes.isEmpty());
	}

	@Test
	public void calculateDeductiableTaxesForYear() {
		PaymentPeriod period2016 = new PaymentPeriod(2016, Quarter.YEAR);
		List<DeductibleTax> taxes = service.calculateDeductedTaxes(user, period2016, new TaxCalculationSettings().splitByQuants(true));

		assertNotNull(taxes);
		assertEquals(10, taxes.size());
		assertTrue(taxes.stream().allMatch(t
				-> t.getCatgory() != null
				&& t.getPayment() != null
				&& t.getPeriod() != null && t.getPeriod().getYear().equals(2016)));
		assertTrue(taxes.stream().anyMatch(t -> TaxCategory.HEALTH_INSURANCE == t.getCatgory() 
				&& t.getPeriod().getQuarter() == Quarter.I 
				&& t.getDeduction() == null));
		assertTrue(taxes.stream().anyMatch(t -> TaxCategory.HEALTH_INSURANCE == t.getCatgory() 
				&& t.getPeriod().getQuarter() == Quarter.II
				&& t.getDeduction() == null));
		assertTrue(taxes.stream().anyMatch(t -> TaxCategory.HEALTH_INSURANCE == t.getCatgory() 
				&& t.getPeriod().getQuarter() == Quarter.III
				&& t.getDeduction() == null));
		assertTrue(taxes.stream().anyMatch(t -> TaxCategory.HEALTH_INSURANCE == t.getCatgory() 
				&& t.getPeriod().getQuarter() == Quarter.IV
				&& t.getDeduction() == null));
		assertTrue(taxes.stream().anyMatch(t -> TaxCategory.PENSION_INSURANCE == t.getCatgory() 
				&& t.getPeriod().getQuarter() == Quarter.I
				&& t.getDeduction() == null));
		assertTrue(taxes.stream().anyMatch(t -> TaxCategory.PENSION_INSURANCE == t.getCatgory() 
				&& t.getPeriod().getQuarter() == Quarter.II
				&& t.getDeduction() == null));
		assertTrue(taxes.stream().anyMatch(t -> TaxCategory.PENSION_INSURANCE == t.getCatgory() 
				&& t.getPeriod().getQuarter() == Quarter.III
				&& t.getDeduction() == null));
		assertTrue(taxes.stream().anyMatch(t -> TaxCategory.PENSION_INSURANCE == t.getCatgory() 
				&& t.getPeriod().getQuarter() == Quarter.IV
				&& t.getDeduction() == null));
		assertTrue(taxes.stream().anyMatch(t -> TaxCategory.INCOME_TAX == t.getCatgory() 
				&& t.getPeriod().getQuarter() == Quarter.II
				&& t.getDeduction() != null));
		assertTrue(taxes.stream().anyMatch(t -> TaxCategory.INCOME_TAX == t.getCatgory() 
				&& t.getPeriod().getQuarter() == Quarter.III
				&& t.getDeduction() != null));

		PaymentPeriod period2015 = new PaymentPeriod(2015, Quarter.YEAR);
		taxes = service.calculateDeductedTaxes(user, period2015);

		assertNotNull(taxes);
		assertEquals(1, taxes.size());
		assertNotNull(taxes.get(0).getCatgory());
		assertNotNull(taxes.get(0).getPayment());
		assertNull(taxes.get(0).getDeduction());
		assertNotNull(taxes.get(0).getPeriod());
		assertEquals(period2015, taxes.get(0).getPeriod());
		assertEquals(TaxCategory.INCOME_TAX, taxes.get(0).getCatgory());
	}
	
	@Test
	public void calculateDeductiableTaxesForYearSummarized() {
		PaymentPeriod period2016 = new PaymentPeriod(2016, Quarter.YEAR);
		List<DeductibleTax> taxes = service.calculateDeductedTaxes(user, period2016, TaxCalculationSettings.defaults());

		assertNotNull(taxes);
		assertEquals(4, taxes.size());
		assertTrue(taxes.stream().allMatch(t
				-> t.getCatgory() != null
				&& t.getPayment() != null
				&& t.getPeriod() != null && t.getPeriod().equals(period2016)));
		assertEquals(1, taxes.stream().filter(t -> TaxCategory.HEALTH_INSURANCE == t.getCatgory() && t.getDeduction() == null).count());
		assertEquals(1, taxes.stream().filter(t -> TaxCategory.PENSION_INSURANCE == t.getCatgory() && t.getDeduction() == null).count());
		assertEquals(1, taxes.stream().filter(t -> TaxCategory.INCOME_TAX == t.getCatgory() && t.getDeduction() != null).count());
		assertEquals(1, taxes.stream().filter(t -> TaxCategory.PENSION_PERCENT == t.getCatgory() && t.getDeduction() == null).count());

		PaymentPeriod period2015 = new PaymentPeriod(2015, Quarter.YEAR);
		taxes = service.calculateDeductedTaxes(user, period2015, TaxCalculationSettings.defaults());

		assertNotNull(taxes);
		assertEquals(1, taxes.size());
		assertNotNull(taxes.get(0).getCatgory());
		assertNotNull(taxes.get(0).getPayment());
		assertNotNull(taxes.get(0).getPeriod());
		assertEquals(period2015, taxes.get(0).getPeriod());
		assertEquals(TaxCategory.INCOME_TAX, taxes.get(0).getCatgory());
		assertNull(taxes.get(0).getDeduction());
	}

	private void prepareFixedPayments() {
		FixedPayment payment1 = generator.generate(FixedPayment.class, TaxCategory.HEALTH_INSURANCE, 2016);
		payment1.setId(null);
		entityManager.persist(payment1);
		FixedPayment payment2 = generator.generate(FixedPayment.class, TaxCategory.PENSION_INSURANCE, 2016);
		payment2.setId(null);
		entityManager.persist(payment2);
	}
	
	private void prepareTestIncomes() {
		Income income1 = generator.generate(Income.class, user, LocalDate.of(2016, Month.APRIL, 5), BigDecimal.valueOf(320000));
		income1.setId(null);
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
