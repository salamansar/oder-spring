package org.salamansar.oder.core.domain.converter;

import java.util.EnumSet;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.salamansar.oder.core.domain.TaxCategory;

/**
 *
 * @author Salamansar
 */
@Converter
public class TaxCategoryConverter implements AttributeConverter<TaxCategory, Long> {

	@Override
	public Long convertToDatabaseColumn(TaxCategory x) {
		return x.getId();
	}

	@Override
	public TaxCategory convertToEntityAttribute(Long y) {
		return EnumSet.allOf(TaxCategory.class).stream()
				.filter(c -> c.getId().equals(y))
				.findAny()
				.orElse(null);
	}
	
}
