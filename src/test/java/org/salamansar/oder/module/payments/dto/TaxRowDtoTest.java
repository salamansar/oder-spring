package org.salamansar.oder.module.payments.dto;

import java.math.BigDecimal;
import org.junit.Test;
import static org.junit.Assert.*;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;

/**
 *
 * @author Salamansar
 */
public class TaxRowDtoTest {
	
	@Test
	public void testQuarterCaption() {
		TaxRowDto dto = new TaxRowDto();
		
		assertNotNull(dto.getPaymentPeriodMessage());
		assertEquals("Суммарно", dto.getPaymentPeriodMessage());
		
		dto.setPaymentPeriod(new PaymentPeriod(2018, Quarter.III));
		
		assertNotNull(dto.getPaymentPeriodMessage());
		assertEquals("III", dto.getPaymentPeriodMessage());
		
		dto.setPaymentPeriod(new PaymentPeriod(2018, Quarter.YEAR));
		
		assertNotNull(dto.getPaymentPeriodMessage());
		assertEquals("Год", dto.getPaymentPeriodMessage());
	}
	
	@Test
	public void testFixedPaymentTaxAmount() {
		TaxRowDto dto = new TaxRowDto();
		
		assertNull(dto.getFixedPaymentsTaxAmount());
		
		dto.setPensionTaxAmount(BigDecimal.valueOf(200));
		
		assertNotNull(dto.getFixedPaymentsTaxAmount());
		assertTrue(dto.getPensionTaxAmount().compareTo(dto.getFixedPaymentsTaxAmount()) == 0);
		
		dto.setHealthInsuranceTaxAmount(BigDecimal.valueOf(100));
		dto.setPensionTaxAmount(null);

		assertNotNull(dto.getFixedPaymentsTaxAmount());
		assertTrue(dto.getHealthInsuranceTaxAmount().compareTo(dto.getFixedPaymentsTaxAmount()) == 0);
		
		dto.setPensionTaxAmount(BigDecimal.valueOf(200));

		assertNotNull(dto.getFixedPaymentsTaxAmount());
		assertTrue(BigDecimal.valueOf(300).compareTo(dto.getFixedPaymentsTaxAmount()) == 0);
	}
	
	@Test
	public void testSummarizedTaxAmount() {
		TaxRowDto dto = new TaxRowDto();
		
		assertNull(dto.getSummarizedTaxAmount());
		
		dto.setPensionTaxAmount(BigDecimal.valueOf(200));
		
		assertNotNull(dto.getSummarizedTaxAmount());
		assertTrue(dto.getPensionTaxAmount().compareTo(dto.getSummarizedTaxAmount()) == 0);
		
		dto.setHealthInsuranceTaxAmount(BigDecimal.valueOf(100));
		dto.setPensionTaxAmount(null);

		assertNotNull(dto.getSummarizedTaxAmount());
		assertTrue(dto.getHealthInsuranceTaxAmount().compareTo(dto.getSummarizedTaxAmount()) == 0);
		
		dto.setPensionTaxAmount(BigDecimal.valueOf(200));

		assertNotNull(dto.getSummarizedTaxAmount());
		assertTrue(BigDecimal.valueOf(300).compareTo(dto.getSummarizedTaxAmount()) == 0);
		
		dto.setHealthInsuranceTaxAmount(null);
		dto.setPensionTaxAmount(null);
		dto.setIncomesTaxAmount(BigDecimal.valueOf(1000));
		
		assertNotNull(dto.getSummarizedTaxAmount());
		assertTrue(dto.getIncomesTaxAmount().compareTo(dto.getSummarizedTaxAmount()) == 0);
		
		dto.setPensionTaxAmount(BigDecimal.valueOf(200));
		dto.setHealthInsuranceTaxAmount(BigDecimal.valueOf(100));

		assertNotNull(dto.getSummarizedTaxAmount());
		assertTrue(BigDecimal.valueOf(1300).compareTo(dto.getSummarizedTaxAmount()) == 0);
	}
	
	@Test
	public void testSummarizedDeductedTaxAmount() {
		TaxRowDto dto = new TaxRowDto();
		
		assertNull(dto.getSummarizedDeductedTaxAmount());
		
		dto.setPensionTaxAmount(BigDecimal.valueOf(200));
		
		assertNotNull(dto.getSummarizedDeductedTaxAmount());
		assertTrue(dto.getPensionTaxAmount().compareTo(dto.getSummarizedDeductedTaxAmount()) == 0);
		
		dto.setHealthInsuranceTaxAmount(BigDecimal.valueOf(100));
		dto.setPensionTaxAmount(null);

		assertNotNull(dto.getSummarizedDeductedTaxAmount());
		assertTrue(dto.getHealthInsuranceTaxAmount().compareTo(dto.getSummarizedDeductedTaxAmount()) == 0);
		
		dto.setPensionTaxAmount(BigDecimal.valueOf(200));

		assertNotNull(dto.getSummarizedDeductedTaxAmount());
		assertTrue(BigDecimal.valueOf(300).compareTo(dto.getSummarizedDeductedTaxAmount()) == 0);
		
		dto.setHealthInsuranceTaxAmount(null);
		dto.setPensionTaxAmount(null);
		dto.setIncomesDeductedTaxAmount(BigDecimal.valueOf(1000));
		
		assertNotNull(dto.getSummarizedDeductedTaxAmount());
		assertTrue(dto.getIncomesDeductedTaxAmount().compareTo(dto.getSummarizedDeductedTaxAmount()) == 0);
		
		dto.setPensionTaxAmount(BigDecimal.valueOf(200));
		dto.setHealthInsuranceTaxAmount(BigDecimal.valueOf(100));

		assertNotNull(dto.getSummarizedDeductedTaxAmount());
		assertTrue(BigDecimal.valueOf(1300).compareTo(dto.getSummarizedDeductedTaxAmount()) == 0);
		
	}
	
}
