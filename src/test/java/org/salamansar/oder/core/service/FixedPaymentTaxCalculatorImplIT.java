package org.salamansar.oder.core.service;

import java.math.BigDecimal;
import java.util.List;
import org.envbuild.environment.DbEnvironmentBuilder;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.salamansar.oder.core.AbstractCoreIntegrationTest;
import org.salamansar.oder.core.domain.FixedPayment;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
import org.salamansar.oder.core.domain.Tax;
import org.salamansar.oder.core.domain.TaxCalculationSettings;
import org.salamansar.oder.core.domain.TaxCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Salamansar
 */
@Transactional
public class FixedPaymentTaxCalculatorImplIT extends AbstractCoreIntegrationTest {
	@Autowired
	private DbEnvironmentBuilder envBuilder;
	@Autowired
	private FixedPaymentTaxCalculator taxCalculator;
	
	@Before
	public void setUp() {
		envBuilder.newBuild()
				.createObject(FixedPayment.class, 2015, BigDecimal.valueOf(32453.5), TaxCategory.PENSION_INSURANCE)
				.createObject(FixedPayment.class, 2015, BigDecimal.valueOf(1470), TaxCategory.HEALTH_INSURANCE);
	}
	
	@Test
	public void calculateQuantized() {
		List<Tax> result = taxCalculator.calculateFixedPayments(
				new PaymentPeriod(2015, Quarter.YEAR), 
				new TaxCalculationSettings().splitByQuants(true).withRoundUp(true));
		
		assertNotNull(result);
		assertEquals(8, result.size());
		
		for(int i=1; i<=4; i++) {
			Quarter quarter = Quarter.fromNumber(i);
			assertTrue(result.stream().anyMatch(t
					-> t.getCatgory() == TaxCategory.PENSION_INSURANCE
					&& new PaymentPeriod(2015, quarter).equals(t.getPeriod())
					&& BigDecimal.valueOf(8113.375).compareTo(t.getPayment()) == 0));
			assertTrue(result.stream().anyMatch(t
					-> t.getCatgory() == TaxCategory.HEALTH_INSURANCE
					&& new PaymentPeriod(2015, quarter).equals(t.getPeriod())
					&& BigDecimal.valueOf(367.5).compareTo(t.getPayment()) == 0));
		}
	}
	
	@Test
	public void calculateSummarized() {
		List<Tax> result = taxCalculator.calculateFixedPayments(
				new PaymentPeriod(2015, Quarter.YEAR),
				new TaxCalculationSettings());

		assertNotNull(result);
		assertEquals(2, result.size());

		assertTrue(result.stream().anyMatch(t
				-> t.getCatgory() == TaxCategory.PENSION_INSURANCE
				&& new PaymentPeriod(2015, Quarter.YEAR).equals(t.getPeriod())
				&& BigDecimal.valueOf(32453.5).compareTo(t.getPayment()) == 0));
		assertTrue(result.stream().anyMatch(t
				-> t.getCatgory() == TaxCategory.HEALTH_INSURANCE
				&& new PaymentPeriod(2015, Quarter.YEAR).equals(t.getPeriod())
				&& BigDecimal.valueOf(1470).compareTo(t.getPayment()) == 0));
	}
	
	@Test
	public void calculateSingleMonth() {
		List<Tax> result = taxCalculator.calculateFixedPayments(
				new PaymentPeriod(2015, Quarter.II),
				new TaxCalculationSettings().withRoundUp(true));

		assertNotNull(result);
		assertEquals(2, result.size());

		assertTrue(result.stream().anyMatch(t
				-> t.getCatgory() == TaxCategory.PENSION_INSURANCE
				&& new PaymentPeriod(2015, Quarter.II).equals(t.getPeriod())
				&& BigDecimal.valueOf(8113.375).compareTo(t.getPayment()) == 0));
		assertTrue(result.stream().anyMatch(t
				-> t.getCatgory() == TaxCategory.HEALTH_INSURANCE
				&& new PaymentPeriod(2015, Quarter.II).equals(t.getPeriod())
				&& BigDecimal.valueOf(367.5).compareTo(t.getPayment()) == 0));
	}
	
}
