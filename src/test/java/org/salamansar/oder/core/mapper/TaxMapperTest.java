package org.salamansar.oder.core.mapper;

import org.envbuild.generator.RandomGenerator;
import static org.junit.Assert.*;
import org.junit.Test;
import org.mapstruct.factory.Mappers;
import org.salamansar.oder.core.domain.DeductibleTax;
import org.salamansar.oder.core.domain.PaymentPeriod;
import org.salamansar.oder.core.domain.Quarter;
import org.salamansar.oder.core.domain.Tax;

/**
 *
 * @author Salamansar
 */
public class TaxMapperTest {
	private TaxMapper mapper = Mappers.getMapper(TaxMapper.class);
	private RandomGenerator generator = new RandomGenerator();
	
	@Test
	public void mapTax() {
		Tax tax = generator.generate(Tax.class, new PaymentPeriod(2018, Quarter.III)); //todo: add recourcive when generation with constructor parameters will be fixed
		
		DeductibleTax result = mapper.mapToDeductible(tax);
		
		assertNotNull(result);
		assertNotNull(result.getCatgory());
		assertEquals(tax.getCatgory(), result.getCatgory());
		assertNotNull(result.getPeriod());
		assertEquals(tax.getPeriod(), result.getPeriod());
		assertNotNull(result.getPayment());
		assertTrue(tax.getPayment().compareTo(result.getPayment()) == 0);
		assertNull(result.getDeduction());
		assertNotNull(result.getDeductedPayment());
		assertTrue(tax.getPayment().compareTo(result.getDeductedPayment()) == 0);
	}
	
}
