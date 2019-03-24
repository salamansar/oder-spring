package org.salamansar.oder.module.payments.adapter;

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
	public void findAllTaxesForYear() {
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
		assertEquals(5, result.size());
		for(TaxRowDto tax : result) {
			assertNotNull(tax.getPaymentPeriod());
			assertNull(tax.getHealthInsuranceTaxAmount());
			assertNull(tax.getIncomesTaxAmount());
			assertNull(tax.getIncomesDeductedTaxAmount());
			assertNull(tax.getOnePercentTaxAmount());
			assertNull(tax.getPensionTaxAmount());
			assertNull(tax.getIncomesAmount());
		}
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
