package org.salamansar.oder.core.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.envbuild.generator.RandomGenerator;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.salamansar.oder.core.component.DeductCombineStrategy;
import org.salamansar.oder.core.domain.DeductibleTax;
import org.salamansar.oder.core.domain.Income;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
import org.salamansar.oder.core.domain.Tax;
import org.salamansar.oder.core.domain.TaxCalculationSettings;
import org.salamansar.oder.core.domain.TaxCategory;
import org.salamansar.oder.core.domain.TaxDeduction;
import org.salamansar.oder.core.domain.User;
import org.salamansar.oder.core.mapper.TaxMapper;

/**
 *
 * @author Salamansar
 */
@RunWith(MockitoJUnitRunner.class)
public class TaxServiceImplTest {
	@Mock
	private IncomeService incomeService;
	@Mock
	private OnePercentTaxCalculator onePercentTaxCalculator;
	@Mock
	private FixedPaymentTaxCalculator fixedPaymentCalculator;
	@Mock
	private IncomeTaxCalculator incomesTaxCalculator;
	@Mock
	private DeductsCalculator deductsCalculator;
	@Spy
	private TaxMapper taxMapper = Mappers.getMapper(TaxMapper.class);
	@Mock
	private DeductCombineStrategy deductCombiner;
	@InjectMocks
	private TaxServiceImpl taxService;
	
	private RandomGenerator generator = new RandomGenerator();
	
	private Income income;
	private User user;
	
	@Before
	public void setUp() {
		income = generator.generate(Income.class);
		user = generator.generate(User.class);
		when(fixedPaymentCalculator.calculateFixedPayments(any(PaymentPeriod.class), any(TaxCalculationSettings.class)))
				.thenReturn(Collections.emptyList());
		when(onePercentTaxCalculator.calculateOnePercentTaxes(any(User.class), any(PaymentPeriod.class), any(TaxCalculationSettings.class)))
				.thenReturn(Collections.emptyList());
		when(deductCombiner.applyDeduct(any(BigDecimal.class), any(BigDecimal.class)))
				.thenReturn(BigDecimal.valueOf(100));
	}
	

	@Test
	public void calculateTaxes() {
		when(incomeService.findIncomes(same(user), any(PaymentPeriod.class)))
				.thenReturn(Arrays.asList(income));
		Tax tax1 = generator.generate(Tax.class);
		Tax tax2 = generator.generate(Tax.class);
		Tax tax3 = generator.generate(Tax.class);
		TaxCalculationSettings defualtSettings = new TaxCalculationSettings();
		when(incomesTaxCalculator.calculateIncomeTaxes(eq(user), any(PaymentPeriod.class), eq(defualtSettings)))
				.thenReturn(Arrays.asList(tax1));
		when(onePercentTaxCalculator.calculateOnePercentTaxes(eq(user), any(PaymentPeriod.class), eq(defualtSettings)))
				.thenReturn(Arrays.asList(tax2, tax3));
		
		List<Tax> result = taxService.calculateTaxes(user, new PaymentPeriod(2018, Quarter.YEAR));
		
		assertNotNull(result);
		assertEquals(3, result.size());
		assertTrue(result.contains(tax1));
		assertTrue(result.contains(tax2));
		assertTrue(result.contains(tax3));
	}
	
	@Test
	public void calculateTaxesWithRoundsUp() {
		when(incomeService.findIncomes(same(user), any(PaymentPeriod.class)))
				.thenReturn(Arrays.asList(income));
		Tax tax1 = generator.generate(Tax.class, BigDecimal.valueOf(100.5));
		Tax tax2 = generator.generate(Tax.class, BigDecimal.valueOf(300.001));
		Tax tax3 = generator.generate(Tax.class, BigDecimal.valueOf(320.99));
		TaxCalculationSettings defualtSettings = new TaxCalculationSettings();
		when(incomesTaxCalculator.calculateIncomeTaxes(eq(user), any(PaymentPeriod.class), eq(defualtSettings)))
				.thenReturn(Arrays.asList(tax1));
		when(fixedPaymentCalculator.calculateFixedPayments(any(PaymentPeriod.class), eq(defualtSettings)))
				.thenReturn(Arrays.asList(tax2));
		when(onePercentTaxCalculator.calculateOnePercentTaxes(eq(user), any(PaymentPeriod.class), eq(defualtSettings)))
				.thenReturn(Arrays.asList(tax3));

		List<Tax> result = taxService.calculateTaxes(user, new PaymentPeriod(2018, Quarter.YEAR), new TaxCalculationSettings().withRoundUp(true));

		assertNotNull(result);
		assertEquals(3, result.size());
		assertTrue(result.contains(tax1));
		assertTrue(result.contains(tax2));
		assertTrue(result.contains(tax3));
		assertTrue(result.stream().anyMatch(t -> BigDecimal.valueOf(101).compareTo(t.getPayment()) == 0));
		assertTrue(result.stream().anyMatch(t -> BigDecimal.valueOf(301).compareTo(t.getPayment()) == 0));
		assertTrue(result.stream().anyMatch(t -> BigDecimal.valueOf(321).compareTo(t.getPayment()) == 0));
	}
	
	@Test
	public void calucalteDeductedTaxesWithDeductsFilled() {
		Tax tax1 = generator.generate(Tax.class, new PaymentPeriod(2018, Quarter.I), TaxCategory.INCOME_TAX);
		Tax tax2 = generator.generate(Tax.class, new PaymentPeriod(2018, Quarter.III), TaxCategory.INCOME_TAX);
		when(incomesTaxCalculator.calculateIncomeTaxes(same(user), any(PaymentPeriod.class), eq(TaxCalculationSettings.defaults())))
				.thenReturn(Arrays.asList(tax1, tax2));
		TaxDeduction deduction1 = generator.generate(TaxDeduction.class, new PaymentPeriod(2018, Quarter.II));
		TaxDeduction deduction2 = generator.generate(TaxDeduction.class, new PaymentPeriod(2018, Quarter.III));
		when(deductsCalculator.calculateDeducts(same(user), any(PaymentPeriod.class), eq(TaxCalculationSettings.defaults())))
				.thenReturn(Arrays.asList(deduction1, deduction2));
		
		List<DeductibleTax> result = taxService.calculateDeductedTaxes(user, new PaymentPeriod(2018, Quarter.YEAR), TaxCalculationSettings.defaults());
		
		assertNotNull(result);
		assertEquals(2, result.size());
		assertTrue(result.stream().allMatch(t -> 
				t.getCatgory() != null 
				&& t.getPayment() != null 
				&& t.getPeriod() != null 
				&& t.getDeductedPayment() != null));
		assertTrue(result.stream().anyMatch(t -> 
				t.getPeriod().getQuarter() == Quarter.I 
				&& t.getDeduction() == null));
		assertTrue(result.stream().anyMatch(t -> 
				t.getPeriod().getQuarter() == Quarter.III 
				&& t.getDeduction() != null));
	}
	
	@Test
	public void calucalteDeductedTaxesWithoutDeducts() {
		Tax tax1 = generator.generate(Tax.class, new PaymentPeriod(2018, Quarter.I), TaxCategory.INCOME_TAX);
		Tax tax2 = generator.generate(Tax.class, new PaymentPeriod(2018, Quarter.III), TaxCategory.INCOME_TAX);
		when(incomesTaxCalculator.calculateIncomeTaxes(same(user), any(PaymentPeriod.class), eq(TaxCalculationSettings.defaults())))
				.thenReturn(Arrays.asList(tax1, tax2));
		when(deductsCalculator.calculateDeducts(same(user), any(PaymentPeriod.class), eq(TaxCalculationSettings.defaults())))
				.thenReturn(Collections.emptyList());

		List<DeductibleTax> result = taxService.calculateDeductedTaxes(user, new PaymentPeriod(2018, Quarter.YEAR), TaxCalculationSettings.defaults());

		assertNotNull(result);
		assertEquals(2, result.size());
		assertTrue(result.stream().allMatch(t
				-> t.getCatgory() != null
				&& t.getPayment() != null
				&& t.getPeriod() != null
				&& t.getDeductedPayment() != null
				&& t.getDeduction() == null));
		assertTrue(result.stream().anyMatch(t
				-> t.getPeriod().getQuarter() == Quarter.I));
		assertTrue(result.stream().anyMatch(t
				-> t.getPeriod().getQuarter() == Quarter.III));
	}
	
	@Test
	public void calucalteDeductedTaxesWithoutTaxes() {
		when(incomesTaxCalculator.calculateIncomeTaxes(same(user), any(PaymentPeriod.class), eq(TaxCalculationSettings.defaults())))
				.thenReturn(Collections.emptyList());
		TaxDeduction deduction1 = generator.generate(TaxDeduction.class, new PaymentPeriod(2018, Quarter.II));
		TaxDeduction deduction2 = generator.generate(TaxDeduction.class, new PaymentPeriod(2018, Quarter.III));
		when(deductsCalculator.calculateDeducts(same(user), any(PaymentPeriod.class), eq(TaxCalculationSettings.defaults())))
				.thenReturn(Arrays.asList(deduction1, deduction2));

		List<DeductibleTax> result = taxService.calculateDeductedTaxes(user, new PaymentPeriod(2018, Quarter.YEAR), TaxCalculationSettings.defaults());

		assertNotNull(result);
		assertEquals(0, result.size());
	}
	
	@Test
	public void calucalteDeductedTaxesWithEmptyCollections() {
		when(incomesTaxCalculator.calculateIncomeTaxes(same(user), any(PaymentPeriod.class), eq(TaxCalculationSettings.defaults())))
				.thenReturn(Collections.emptyList());
		when(deductsCalculator.calculateDeducts(same(user), any(PaymentPeriod.class), eq(TaxCalculationSettings.defaults())))
				.thenReturn(Collections.emptyList());

		List<DeductibleTax> result = taxService.calculateDeductedTaxes(user, new PaymentPeriod(2018, Quarter.YEAR), TaxCalculationSettings.defaults());

		assertNotNull(result);
		assertEquals(0, result.size());
	}
	
	@Test
	public void calucalteRoundedDeductedTaxes() {
		Tax tax1 = generator.generate(Tax.class, new PaymentPeriod(2018, Quarter.I), TaxCategory.INCOME_TAX, BigDecimal.valueOf(100.7));
		Tax tax2 = generator.generate(Tax.class, new PaymentPeriod(2018, Quarter.III), TaxCategory.INCOME_TAX, BigDecimal.valueOf(300.001));
		Tax tax3 = generator.generate(Tax.class, new PaymentPeriod(2018, Quarter.IV), TaxCategory.INCOME_TAX, BigDecimal.valueOf(600.86));
		when(incomesTaxCalculator.calculateIncomeTaxes(same(user), any(PaymentPeriod.class), eq(TaxCalculationSettings.defaults())))
				.thenReturn(Arrays.asList(tax1, tax2, tax3));
		TaxDeduction deduction1 = generator.generate(TaxDeduction.class, new PaymentPeriod(2018, Quarter.II), BigDecimal.valueOf(100.5));
		TaxDeduction deduction2 = generator.generate(TaxDeduction.class, new PaymentPeriod(2018, Quarter.III), BigDecimal.valueOf(180.25));
		TaxDeduction deduction3 = generator.generate(TaxDeduction.class, new PaymentPeriod(2018, Quarter.IV), BigDecimal.valueOf(180.25));
		when(deductsCalculator.calculateDeducts(same(user), any(PaymentPeriod.class), eq(TaxCalculationSettings.defaults())))
				.thenReturn(Arrays.asList(deduction1, deduction2, deduction3));
		when(deductCombiner.applyDeduct(any(BigDecimal.class), any(BigDecimal.class)))
				.thenAnswer(inv -> {
					BigDecimal par1 = inv.getArgumentAt(0, BigDecimal.class);
					BigDecimal par2 = inv.getArgumentAt(1, BigDecimal.class);
					return par2 == null ? par1 : par1.subtract(par2);
				});

		List<DeductibleTax> result = taxService.calculateDeductedTaxes(
				user, 
				new PaymentPeriod(2018, Quarter.YEAR), 
				new TaxCalculationSettings().withRoundUp(true)
		);

		assertNotNull(result);
		assertEquals(3, result.size());
		assertTrue(result.stream().allMatch(t
				-> t.getCatgory() != null
				&& t.getPayment() != null
				&& t.getPeriod() != null
				&& t.getDeductedPayment() != null));
		
		Optional<DeductibleTax> firstQuarterTax = result.stream().filter(t
				-> t.getPeriod().getQuarter() == Quarter.I)
				.findAny();
		assertTrue(firstQuarterTax.isPresent());
		assertNull(firstQuarterTax.get().getDeduction());
		assertTrue(BigDecimal.valueOf(101).compareTo(firstQuarterTax.get().getPayment()) == 0);
		assertTrue(BigDecimal.valueOf(101).compareTo(firstQuarterTax.get().getDeductedPayment()) == 0);

		Optional<DeductibleTax> thirdQuarterTax = result.stream().filter(t
				-> t.getPeriod().getQuarter() == Quarter.III)
				.findAny();
		assertTrue(thirdQuarterTax.isPresent());
		assertNotNull(thirdQuarterTax.get().getDeduction());
		assertTrue(BigDecimal.valueOf(180.25).compareTo(thirdQuarterTax.get().getDeduction()) == 0);
		assertTrue(BigDecimal.valueOf(301).compareTo(thirdQuarterTax.get().getPayment()) == 0);
		assertTrue(BigDecimal.valueOf(120).compareTo(thirdQuarterTax.get().getDeductedPayment()) == 0);
		
		Optional<DeductibleTax> fourthQuarterTax = result.stream().filter(t
				-> t.getPeriod().getQuarter() == Quarter.IV)
				.findAny();
		assertTrue(fourthQuarterTax.isPresent());
		assertNotNull(fourthQuarterTax.get().getDeduction());
		assertTrue(BigDecimal.valueOf(180.25).compareTo(fourthQuarterTax.get().getDeduction()) == 0);
		assertTrue(BigDecimal.valueOf(601).compareTo(fourthQuarterTax.get().getPayment()) == 0);
		assertTrue(BigDecimal.valueOf(421).compareTo(fourthQuarterTax.get().getDeductedPayment()) == 0);
	}
}
