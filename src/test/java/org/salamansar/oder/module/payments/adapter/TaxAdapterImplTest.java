package org.salamansar.oder.module.payments.adapter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.envbuild.check.CheckCommonUtils.checkList;
import org.envbuild.generator.RandomGenerator;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.salamansar.oder.core.domain.DeductibleTax;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
import org.salamansar.oder.core.domain.QuarterIncome;
import org.salamansar.oder.core.domain.TaxCalculationSettings;
import org.salamansar.oder.core.domain.TaxCategory;
import org.salamansar.oder.core.domain.User;
import org.salamansar.oder.core.service.IncomeService;
import org.salamansar.oder.core.service.TaxService;
import org.salamansar.oder.module.payments.dto.TaxRowDto;

/**
 *
 * @author Salamansar
 */
@RunWith(MockitoJUnitRunner.class)
public class TaxAdapterImplTest {
	@Mock
	private TaxService taxService;
	@Mock
	private IncomeService incomeService;
	@InjectMocks
	private TaxAdapterImpl adapter = new TaxAdapterImpl();
	private RandomGenerator generator = new RandomGenerator();
	private User user = new User();
	private Integer year = 2018;

	@Test
	public void findTaxesForYear() {
		DeductibleTax incomeTax1 = generator.generate(DeductibleTax.class, TaxCategory.INCOME_TAX, new PaymentPeriod(year, Quarter.I));
		DeductibleTax incomeTax2 = generator.generate(DeductibleTax.class, TaxCategory.INCOME_TAX, new PaymentPeriod(year, Quarter.II));
		DeductibleTax pensionTax1 = generator.generate(DeductibleTax.class, TaxCategory.PENSION_INSURANCE, new PaymentPeriod(year, Quarter.II));
		DeductibleTax pensionTax2 = generator.generate(DeductibleTax.class, TaxCategory.PENSION_INSURANCE, new PaymentPeriod(year, Quarter.III));
		DeductibleTax healthTax1 = generator.generate(DeductibleTax.class, TaxCategory.HEALTH_INSURANCE, new PaymentPeriod(year, Quarter.II));
		DeductibleTax healthTax2 = generator.generate(DeductibleTax.class, TaxCategory.HEALTH_INSURANCE, new PaymentPeriod(year, Quarter.III));
		DeductibleTax onePercentTax1 = generator.generate(DeductibleTax.class, TaxCategory.PENSION_PERCENT, new PaymentPeriod(year, Quarter.II));
		QuarterIncome income1 = generator.generate(QuarterIncome.class, new PaymentPeriod(year, Quarter.I));
		QuarterIncome income2 = generator.generate(QuarterIncome.class, new PaymentPeriod(year, Quarter.II));
		when(taxService.calculateDeductedTaxes(same(user), 
				eq(new PaymentPeriod(year, Quarter.YEAR)), 
				eq(new TaxCalculationSettings().splitByQuants(true).withRoundUp(true))))
				.thenReturn(Arrays.asList(healthTax2, incomeTax2, incomeTax1, pensionTax1, pensionTax2, healthTax1, onePercentTax1));
		when(incomeService.findQuarterIncomes(same(user), eq(new PaymentPeriod(year, Quarter.YEAR)), eq(true)))
				.thenReturn(Arrays.asList(income2, income1));
		
		List<TaxRowDto> result = adapter.findTaxesForYear(user, year, true);
		
		assertNotNull(result);
		assertEquals(4, result.size());
		
		TaxRowDto firstTaxRow = result.get(0);
		assertNotNull(firstTaxRow);
		assertNotNull(firstTaxRow.getPaymentPeriod());
		assertEquals(new PaymentPeriod(year, Quarter.I), firstTaxRow.getPaymentPeriod());
		assertNull(firstTaxRow.getHealthInsuranceTaxAmount());
		assertNotNull(firstTaxRow.getIncomesTaxAmount());
		assertTrue(incomeTax1.getPayment().compareTo(firstTaxRow.getIncomesTaxAmount()) == 0);
		assertNotNull(firstTaxRow.getIncomesDeductedTaxAmount());
		assertNull(firstTaxRow.getOnePercentTaxAmount());
		assertNull(firstTaxRow.getPensionTaxAmount());
		assertNotNull(firstTaxRow.getIncomesAmount());
		assertTrue(income1.getIncomeAmount().compareTo(firstTaxRow.getIncomesAmount()) == 0);
		
		TaxRowDto secondTaxRow = result.get(1);
		assertNotNull(secondTaxRow);
		assertNotNull(secondTaxRow.getPaymentPeriod());
		assertEquals(new PaymentPeriod(year, Quarter.II), secondTaxRow.getPaymentPeriod());
		assertNotNull(secondTaxRow.getHealthInsuranceTaxAmount());
		assertTrue(healthTax1.getPayment().compareTo(secondTaxRow.getHealthInsuranceTaxAmount()) == 0);
		assertNotNull(secondTaxRow.getIncomesTaxAmount());
		assertTrue(incomeTax2.getPayment().compareTo(secondTaxRow.getIncomesTaxAmount()) == 0);
		assertNotNull(secondTaxRow.getIncomesDeductedTaxAmount());
		assertNotNull(secondTaxRow.getOnePercentTaxAmount());
		assertTrue(onePercentTax1.getPayment().compareTo(secondTaxRow.getOnePercentTaxAmount()) == 0);
		assertNotNull(secondTaxRow.getPensionTaxAmount());
		assertTrue(pensionTax1.getPayment().compareTo(secondTaxRow.getPensionTaxAmount()) == 0);
		assertNotNull(secondTaxRow.getIncomesAmount());
		assertTrue(income2.getIncomeAmount().compareTo(secondTaxRow.getIncomesAmount()) == 0);
		
		TaxRowDto thirdTaxRow = result.get(2);
		assertNotNull(thirdTaxRow);
		assertNotNull(thirdTaxRow.getPaymentPeriod());
		assertEquals(new PaymentPeriod(year, Quarter.III), thirdTaxRow.getPaymentPeriod());
		assertNotNull(thirdTaxRow.getHealthInsuranceTaxAmount());
		assertTrue(healthTax2.getPayment().compareTo(thirdTaxRow.getHealthInsuranceTaxAmount()) == 0);
		assertNull(thirdTaxRow.getIncomesTaxAmount());
		assertNull(thirdTaxRow.getIncomesDeductedTaxAmount());
		assertNull(thirdTaxRow.getOnePercentTaxAmount());
		assertNotNull(thirdTaxRow.getPensionTaxAmount());
		assertTrue(pensionTax2.getPayment().compareTo(thirdTaxRow.getPensionTaxAmount()) == 0);
		assertNull(thirdTaxRow.getIncomesAmount());

		TaxRowDto fourthTaxRow = result.get(3);
		assertNotNull(fourthTaxRow);
		assertNotNull(fourthTaxRow.getPaymentPeriod());
		assertEquals(new PaymentPeriod(year, Quarter.IV), fourthTaxRow.getPaymentPeriod());
		assertNull(fourthTaxRow.getHealthInsuranceTaxAmount());
		assertNull(fourthTaxRow.getIncomesTaxAmount());
		assertNull(fourthTaxRow.getIncomesDeductedTaxAmount());
		assertNull(fourthTaxRow.getOnePercentTaxAmount());
		assertNull(fourthTaxRow.getPensionTaxAmount());
		assertNull(fourthTaxRow.getIncomesAmount());
	}
	
	@Test
	public void findSummarizedTaxesForYear() {
		DeductibleTax incomeTax = generator.generate(DeductibleTax.class, TaxCategory.INCOME_TAX, new PaymentPeriod(year, Quarter.I));
		DeductibleTax pensionTax = generator.generate(DeductibleTax.class, TaxCategory.PENSION_INSURANCE, new PaymentPeriod(year, Quarter.II));
		DeductibleTax healthTax = generator.generate(DeductibleTax.class, TaxCategory.HEALTH_INSURANCE, new PaymentPeriod(year, Quarter.II));
		DeductibleTax onePercentTax = generator.generate(DeductibleTax.class, TaxCategory.PENSION_PERCENT, new PaymentPeriod(year, Quarter.II));
		QuarterIncome income = generator.generate(QuarterIncome.class, new PaymentPeriod(year, Quarter.YEAR));
		when(taxService.calculateDeductedTaxes(same(user), 
				eq(new PaymentPeriod(year, Quarter.YEAR)), 
				eq(new TaxCalculationSettings().withRoundUp(true))))
				.thenReturn(Arrays.asList(incomeTax, pensionTax, healthTax, onePercentTax));
		when(incomeService.findSummaryYearIncome(same(user), eq(year)))
				.thenReturn(income);
		
		TaxRowDto result = adapter.findSummarizedTaxesForYear(user, year, true);
		
		assertNotNull(result);		
		assertNotNull(result.getPaymentPeriod());
		assertEquals(new PaymentPeriod(year, Quarter.YEAR), result.getPaymentPeriod());
		assertNotNull(result.getHealthInsuranceTaxAmount());
		assertTrue(healthTax.getPayment().compareTo(result.getHealthInsuranceTaxAmount()) == 0);
		assertNotNull(result.getIncomesTaxAmount());
		assertTrue(incomeTax.getPayment().compareTo(result.getIncomesTaxAmount()) == 0);
		assertNotNull(result.getIncomesDeductedTaxAmount());
		assertNotNull(result.getOnePercentTaxAmount());
		assertTrue(onePercentTax.getPayment().compareTo(result.getOnePercentTaxAmount()) == 0);
		assertNotNull(result.getPensionTaxAmount());
		assertTrue(pensionTax.getPayment().compareTo(result.getPensionTaxAmount()) == 0);
		assertNotNull(result.getIncomesAmount());
		assertTrue(income.getIncomeAmount().compareTo(result.getIncomesAmount()) == 0);
	}
	
	@Test
	public void findAllTaxesForYearWithEmptyTaxes() {
		when(taxService.calculateDeductedTaxes(same(user), 
				eq(new PaymentPeriod(year, Quarter.YEAR)), 
				any(TaxCalculationSettings.class)))
				.thenReturn(Collections.emptyList());
		when(incomeService.findQuarterIncomes(same(user), 
				eq(new PaymentPeriod(year, Quarter.YEAR)), 
				anyBoolean()))
				.thenReturn(Collections.emptyList());
		
		List<TaxRowDto> result = adapter.findAllTaxesForYear(user, year, false);
		
		assertNotNull(result);		
		assertEquals(6, result.size());
		for(int i = 0; i < 6; i++){
			TaxRowDto tax = result.get(i);
			if(i == 4) {
				assertNull(tax.getPaymentPeriod());
			} else {
				assertNotNull(tax.getPaymentPeriod());
			}
			assertNull(tax.getHealthInsuranceTaxAmount());
			assertNull(tax.getIncomesTaxAmount());
			assertNull(tax.getIncomesDeductedTaxAmount());
			assertNull(tax.getOnePercentTaxAmount());
			assertNull(tax.getPensionTaxAmount());
			assertNull(tax.getIncomesAmount());
		}
	}
	
	@Test
	public void summaryCalculation() {
		DeductibleTax incomeTax1 = new DeductibleTax();
		incomeTax1.setCatgory(TaxCategory.INCOME_TAX);
		incomeTax1.setPeriod(new PaymentPeriod(year, Quarter.I));
		incomeTax1.setPayment(BigDecimal.valueOf(600.5));
		incomeTax1.setDeduction(BigDecimal.valueOf(300.05));
		incomeTax1.setDeductedPayment(BigDecimal.valueOf(300.45));
		DeductibleTax incomeTax2 = new DeductibleTax();
		incomeTax2.setCatgory(TaxCategory.INCOME_TAX);
		incomeTax2.setPeriod(new PaymentPeriod(year, Quarter.II));
		incomeTax2.setPayment(BigDecimal.valueOf(300.5));
		incomeTax2.setDeduction(BigDecimal.valueOf(325.0));
		incomeTax2.setDeductedPayment(BigDecimal.valueOf(0));
		DeductibleTax pensionTax1 = new DeductibleTax();
		pensionTax1.setCatgory(TaxCategory.PENSION_INSURANCE);
		pensionTax1.setPeriod(new PaymentPeriod(year, Quarter.II));
		pensionTax1.setPayment(BigDecimal.valueOf(400.25));
		pensionTax1.setDeductedPayment(BigDecimal.valueOf(400.25));
		DeductibleTax pensionTax2 = new DeductibleTax();
		pensionTax2.setCatgory(TaxCategory.PENSION_INSURANCE);
		pensionTax2.setPeriod(new PaymentPeriod(year, Quarter.III));
		pensionTax2.setPayment(BigDecimal.valueOf(400.25));
		pensionTax2.setDeductedPayment(BigDecimal.valueOf(400.25));
		DeductibleTax healthTax1 = new DeductibleTax();
		healthTax1.setCatgory(TaxCategory.HEALTH_INSURANCE);
		healthTax1.setPeriod(new PaymentPeriod(year, Quarter.II));
		healthTax1.setPayment(BigDecimal.valueOf(120.02));
		healthTax1.setDeductedPayment(BigDecimal.valueOf(120.02));
		QuarterIncome income1 = generator.generate(QuarterIncome.class, new PaymentPeriod(year, Quarter.I), BigDecimal.valueOf(20000));
		QuarterIncome income2 = generator.generate(QuarterIncome.class, new PaymentPeriod(year, Quarter.II), BigDecimal.valueOf(120000.5));
		when(taxService.calculateDeductedTaxes(same(user),
				eq(new PaymentPeriod(year, Quarter.YEAR)),
				eq(new TaxCalculationSettings().splitByQuants(true))))
				.thenReturn(Arrays.asList(incomeTax2, incomeTax1, pensionTax1, pensionTax2, healthTax1));
		when(incomeService.findQuarterIncomes(same(user), eq(new PaymentPeriod(year, Quarter.YEAR)), eq(true)))
				.thenReturn(Arrays.asList(income2, income1));
		when(taxService.calculateDeductedTaxes(same(user),
				eq(new PaymentPeriod(year, Quarter.YEAR)),
				eq(new TaxCalculationSettings())))
				.thenReturn(Collections.emptyList());

		List<TaxRowDto> result = adapter.findAllTaxesForYear(user, year, false);

		assertNotNull(result);
		assertEquals(6, result.size());

		TaxRowDto firstTaxRow = result.get(0);
		assertNotNull(firstTaxRow);
		assertNotNull(firstTaxRow.getPaymentPeriod());
		assertEquals(new PaymentPeriod(year, Quarter.I), firstTaxRow.getPaymentPeriod());
		assertNull(firstTaxRow.getHealthInsuranceTaxAmount());
		assertNotNull(firstTaxRow.getIncomesTaxAmount());
		assertTrue(BigDecimal.valueOf(600.5).compareTo(firstTaxRow.getIncomesTaxAmount()) == 0);
		assertNotNull(firstTaxRow.getIncomesDeductedTaxAmount());
		assertTrue(BigDecimal.valueOf(300.45).compareTo(firstTaxRow.getIncomesDeductedTaxAmount()) == 0);
		assertNull(firstTaxRow.getOnePercentTaxAmount());
		assertNull(firstTaxRow.getPensionTaxAmount());
		assertNotNull(firstTaxRow.getIncomesAmount());
		assertTrue(BigDecimal.valueOf(20000).compareTo(firstTaxRow.getIncomesAmount()) == 0);

		TaxRowDto secondTaxRow = result.get(1);
		assertNotNull(secondTaxRow);
		assertNotNull(secondTaxRow.getPaymentPeriod());
		assertEquals(new PaymentPeriod(year, Quarter.II), secondTaxRow.getPaymentPeriod());
		assertNotNull(secondTaxRow.getHealthInsuranceTaxAmount());
		assertTrue(BigDecimal.valueOf(120.02).compareTo(secondTaxRow.getHealthInsuranceTaxAmount()) == 0);
		assertNotNull(secondTaxRow.getIncomesTaxAmount());
		assertTrue(BigDecimal.valueOf(300.5).compareTo(secondTaxRow.getIncomesTaxAmount()) == 0);
		assertNotNull(secondTaxRow.getIncomesDeductedTaxAmount());
		assertTrue(BigDecimal.valueOf(0).compareTo(secondTaxRow.getIncomesDeductedTaxAmount()) == 0);
		assertNull(secondTaxRow.getOnePercentTaxAmount());
		assertNotNull(secondTaxRow.getPensionTaxAmount());
		assertTrue(BigDecimal.valueOf(400.25).compareTo(secondTaxRow.getPensionTaxAmount()) == 0);
		assertNotNull(secondTaxRow.getIncomesAmount());
		assertTrue(BigDecimal.valueOf(120000.5).compareTo(secondTaxRow.getIncomesAmount()) == 0);

		TaxRowDto thirdTaxRow = result.get(2);
		assertNotNull(thirdTaxRow);
		assertNotNull(thirdTaxRow.getPaymentPeriod());
		assertEquals(new PaymentPeriod(year, Quarter.III), thirdTaxRow.getPaymentPeriod());
		assertNull(thirdTaxRow.getHealthInsuranceTaxAmount());
		assertNull(thirdTaxRow.getIncomesTaxAmount());
		assertNull(thirdTaxRow.getIncomesDeductedTaxAmount());
		assertNull(thirdTaxRow.getOnePercentTaxAmount());
		assertNotNull(thirdTaxRow.getPensionTaxAmount());
		assertTrue(BigDecimal.valueOf(400.25).compareTo(thirdTaxRow.getPensionTaxAmount()) == 0);
		assertNull(thirdTaxRow.getIncomesAmount());

		TaxRowDto fourthTaxRow = result.get(3);
		assertNotNull(fourthTaxRow);
		assertNotNull(fourthTaxRow.getPaymentPeriod());
		assertEquals(new PaymentPeriod(year, Quarter.IV), fourthTaxRow.getPaymentPeriod());
		assertNull(fourthTaxRow.getHealthInsuranceTaxAmount());
		assertNull(fourthTaxRow.getIncomesTaxAmount());
		assertNull(fourthTaxRow.getIncomesDeductedTaxAmount());
		assertNull(fourthTaxRow.getOnePercentTaxAmount());
		assertNull(fourthTaxRow.getPensionTaxAmount());
		assertNull(fourthTaxRow.getIncomesAmount());
		
		TaxRowDto fifthTaxRow = result.get(4);
		assertNotNull(fifthTaxRow);
		assertNull(fifthTaxRow.getPaymentPeriod());
		assertNotNull(fifthTaxRow.getHealthInsuranceTaxAmount());
		assertTrue(BigDecimal.valueOf(120.02).compareTo(fifthTaxRow.getHealthInsuranceTaxAmount()) == 0);
		assertNotNull(fifthTaxRow.getIncomesTaxAmount());
		assertTrue(BigDecimal.valueOf(901).compareTo(fifthTaxRow.getIncomesTaxAmount()) == 0);
		assertNotNull(fifthTaxRow.getIncomesDeductedTaxAmount());
		assertTrue(BigDecimal.valueOf(300.45).compareTo(fifthTaxRow.getIncomesDeductedTaxAmount()) == 0);
		assertNull(fifthTaxRow.getOnePercentTaxAmount());
		assertNotNull(fifthTaxRow.getPensionTaxAmount());
		assertTrue(BigDecimal.valueOf(800.5).compareTo(fifthTaxRow.getPensionTaxAmount()) == 0);
		assertNotNull(fifthTaxRow.getIncomesAmount());
		assertTrue(BigDecimal.valueOf(140000.5).compareTo(fifthTaxRow.getIncomesAmount()) == 0);
		
		TaxRowDto sixthTaxRow = result.get(5);
		assertNotNull(sixthTaxRow);
		assertNotNull(sixthTaxRow.getPaymentPeriod());
		assertEquals(new PaymentPeriod(year, Quarter.YEAR), sixthTaxRow.getPaymentPeriod());
		assertNull(sixthTaxRow.getHealthInsuranceTaxAmount());
		assertNull(sixthTaxRow.getIncomesTaxAmount());
		assertNull(sixthTaxRow.getIncomesDeductedTaxAmount());
		assertNull(sixthTaxRow.getOnePercentTaxAmount());
		assertNull(sixthTaxRow.getPensionTaxAmount());
		assertNull(sixthTaxRow.getIncomesAmount());
	}
	
	@Test
	public void findYearsWithIncomes() {
		when(incomeService.findYearsWithIncomes(same(user)))
				.thenReturn(Arrays.asList(2016, 2018));
		
		List<Integer> result = adapter.findYearsWithIncomes(user);
		
		checkList(result, 2);
		assertEquals(2018, result.get(0).intValue());
		assertEquals(2016, result.get(1).intValue());
	}
}
