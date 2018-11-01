package org.salamansar.oder.core.domain.converter;

import org.junit.Test;
import static org.junit.Assert.*;
import org.salamansar.oder.core.domain.TaxCategory;

/**
 *
 * @author Salamansar
 */
public class TaxCategoryConverterTest {
	
	private TaxCategoryConverter converter = new TaxCategoryConverter();

	@Test
	public void convertToColumn() {
		Long result = converter.convertToDatabaseColumn(TaxCategory.INCOME_TAX);
		
		assertNotNull(result);
		assertEquals(3, result.longValue());
		
		result = converter.convertToDatabaseColumn(null);
		
		assertNull(result);
	}
	
	@Test
	public void convertFromColumn() {
		TaxCategory result = converter.convertToEntityAttribute(3L);
		
		assertNotNull(result);
		assertEquals(TaxCategory.INCOME_TAX, result);
		
		result = converter.convertToEntityAttribute(null);
		
		assertNull(result);
	}
	
	
}
