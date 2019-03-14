package org.salamansar.oder.core.mapper;

import org.mapstruct.Mapper;
import org.salamansar.oder.core.domain.DeductibleTax;
import org.salamansar.oder.core.domain.Tax;

/**
 *
 * @author Salamansar
 */
@Mapper
public interface TaxMapper {
	
	DeductibleTax mapToDeductible(Tax tax);
	
}
